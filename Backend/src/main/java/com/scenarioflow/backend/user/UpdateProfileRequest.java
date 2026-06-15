package com.scenarioflow.backend.user;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String fullName;

    @Email
    private String email;

    private String currentPassword;
    private String newPassword;
}