package com.franchise.data.dtos.request;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String staffId;
}
