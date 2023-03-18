package com.franchise.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@Builder
public class ApiResponse {
    private ZonedDateTime timeStamp;
    private HttpStatus statusCode;
    private String path;
    private Object data;
    private Boolean isSuccessful;
}
