package com.franchise.data.dtos.request;

import com.franchise.data.models.ElectionType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateElectionRequest {
    @NotBlank(message = "This field is required")
    private String electionDate;
    @NotBlank(message = "This field is required")
    private ElectionType electionType;
    @NotBlank(message = "This field is required")
    private List<CandidateRequest> candidateRequest = new ArrayList<>();
}
