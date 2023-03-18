package com.franchise.data.dtos.request;

import com.franchise.utils.Validators;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;

@Data
public class ChangePasswordRequest {
    @NotBlank(message = "This field must not be empty")
    private String oldPassword;
    @NotBlank(message = "This field must not be empty")
    private String newPassword;

    public String getNewPassword() {
        if(Validators.validatePassword(newPassword))
            return BCrypt.hashpw(newPassword, BCrypt.gensalt());
        else
            throw new RuntimeException("password must contain at least one " +
                    "capital letter, small letter and special characters");
    }
}
