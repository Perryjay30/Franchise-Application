package com.franchise.data.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CandidateRequest {
    @NotBlank(message = "This field is required")
    private String firstName;
    @NotBlank(message = "This field is required")
    private String lastName;
    @NotBlank(message = "This field is required")
    private String aspiringPosition;
}
