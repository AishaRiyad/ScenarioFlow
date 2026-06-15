package com.scenarioflow.backend.user;

import com.scenarioflow.backend.attempt.AttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AttemptRepository attemptRepository;
    private final PasswordEncoder passwordEncoder;

    public UserProfileResponse getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var attempts = attemptRepository.findByUser(user);

        long completed = attempts.stream()
                .filter(a -> Boolean.TRUE.equals(a.getCompleted()))
                .count();

        int bestScore = attempts.stream()
                .filter(a -> a.getFinalScore() != null)
                .mapToInt(a -> a.getFinalScore())
                .max()
                .orElse(0);

        double averageScore = attempts.stream()
                .filter(a -> a.getFinalScore() != null)
                .mapToInt(a -> a.getFinalScore())
                .average()
                .orElse(0);

        return UserProfileResponse.builder()
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .totalAttempts(attempts.size())
                .completedAttempts(completed)
                .bestScore(bestScore)
                .averageScore(averageScore)
                .build();
    }

    public UserProfileResponse updateProfile(
            String currentEmail,
            UpdateProfileRequest request
    ) {
        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getFullName() != null &&
                !request.getFullName().isBlank()) {
            user.setFullName(request.getFullName());
        }

        if (request.getEmail() != null &&
                !request.getEmail().isBlank() &&
                !request.getEmail().equals(user.getEmail())) {

            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already exists");
            }

            user.setEmail(request.getEmail());
        }

        if (request.getNewPassword() != null &&
                !request.getNewPassword().isBlank()) {

            if (request.getCurrentPassword() == null ||
                    request.getCurrentPassword().isBlank()) {
                throw new RuntimeException("Current password is required");
            }

            if (!passwordEncoder.matches(
                    request.getCurrentPassword(),
                    user.getPassword()
            )) {
                throw new RuntimeException("Current password is incorrect");
            }

            user.setPassword(
                    passwordEncoder.encode(request.getNewPassword())
            );
        }

        userRepository.save(user);

        return getProfile(user.getEmail());
    }

    public List<AchievementResponse> getAchievements(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var attempts = attemptRepository.findByUser(user);

        long completed = attempts.stream()
                .filter(a -> Boolean.TRUE.equals(a.getCompleted()))
                .count();

        int bestScore = attempts.stream()
                .filter(a -> a.getFinalScore() != null)
                .mapToInt(a -> a.getFinalScore())
                .max()
                .orElse(0);

        return List.of(
                AchievementResponse.builder()
                        .title("First Step")
                        .description("Start your first scenario attempt.")
                        .icon("🌱")
                        .unlocked(!attempts.isEmpty())
                        .build(),

                AchievementResponse.builder()
                        .title("Scenario Finisher")
                        .description("Complete your first scenario.")
                        .icon("🏁")
                        .unlocked(completed >= 1)
                        .build(),

                AchievementResponse.builder()
                        .title("High Scorer")
                        .description("Reach a score of 20 or more.")
                        .icon("⭐")
                        .unlocked(bestScore >= 20)
                        .build(),

                AchievementResponse.builder()
                        .title("Decision Master")
                        .description("Complete at least 5 scenarios.")
                        .icon("👑")
                        .unlocked(completed >= 5)
                        .build()
        );
    }
}