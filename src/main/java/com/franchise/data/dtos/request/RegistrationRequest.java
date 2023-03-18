package com.franchise.data.dtos.request;

import com.franchise.utils.Validators;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;

@Data
public class RegistrationRequest {
    private String emailAddress;
    private String password;
    private String firstName;
    private String lastName;

    public String getPassword() {
        if(Validators.validatePassword(password))
            return BCrypt.hashpw(password, BCrypt.gensalt());
        else
            throw new RuntimeException("password must contain at least one " +
                    "capital letter, small letter and special characters");
    }
}
