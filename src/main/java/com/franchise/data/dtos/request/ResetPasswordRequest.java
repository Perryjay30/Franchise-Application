package com.franchise.data.dtos.request;

import com.franchise.utils.Validators;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;

@Data
public class ResetPasswordRequest {
    @NotBlank(message = "This field must not be empty")
    private String password;
    @NotBlank(message = "This field must not be empty")
    private String confirmPassword;
    @NotBlank(message = "This field must not be empty")
    private String token;

    public String getPassword() {
        if(Validators.validatePassword(password))
            return BCrypt.hashpw(password, BCrypt.gensalt());
        else
            throw new RuntimeException("password must contain at least one " +
                    "capital letter, small letter and special characters");
    }
}
