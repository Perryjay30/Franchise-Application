package com.franchise.data.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "required")
    private String emailAddress;
    @NotBlank(message = "required")
    private String password;
}
