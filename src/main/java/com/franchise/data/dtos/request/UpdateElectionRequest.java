package com.franchise.data.dtos.request;

import com.franchise.data.models.ElectionType;
import lombok.Data;

@Data
public class UpdateElectionRequest {
    private String electionDate;
    private ElectionType electionType;
    private CandidateRequest updateCandidateRequest;

}
