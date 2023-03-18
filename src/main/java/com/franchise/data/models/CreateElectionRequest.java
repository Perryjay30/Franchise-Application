package com.franchise.data.models;

import com.franchise.data.dtos.request.CandidateRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateElectionRequest {
    @NotBlank(message = "This field is required")
    private String electionDate;
    @NotBlank(message = "This field is required")
    private ElectionType electionType;
    @NotBlank(message = "This field is required")
    private CandidateRequest candidateRequest;
}
