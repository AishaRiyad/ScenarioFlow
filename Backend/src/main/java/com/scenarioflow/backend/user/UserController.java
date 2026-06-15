package com.scenarioflow.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserProfileResponse getMe(Authentication auth) {
        return userService.getProfile(auth.getName());
    }

    @PutMapping("/me")
    public UserProfileResponse updateMe(
            Authentication auth,
            @RequestBody UpdateProfileRequest request
    ) {
        return userService.updateProfile(auth.getName(), request);
    }
}