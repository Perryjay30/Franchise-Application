package com.franchise.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class Feedback {
    private String userId;
    private String message;
    private int statusCode;
}
