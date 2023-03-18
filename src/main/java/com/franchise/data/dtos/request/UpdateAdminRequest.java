package com.franchise.data.dtos.request;

import com.franchise.data.models.Role;
import lombok.Data;

@Data
public class UpdateAdminRequest {
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String staffId;
    private String uniqueAdminId;
    private Role userRole;
}
