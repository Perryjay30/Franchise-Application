package com.franchise.data.dtos.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String emailAddress;
    private String password;
}
