package com.franchise.data.dtos.request;


import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String emailAddress;
    private String token;
}
