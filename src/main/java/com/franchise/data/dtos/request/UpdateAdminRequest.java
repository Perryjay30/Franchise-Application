package com.franchise.data.dtos.request;

import lombok.Data;

@Data
public class UpdateAdminRequest {
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String staffId;
    private String uniqueAdminId;
}
