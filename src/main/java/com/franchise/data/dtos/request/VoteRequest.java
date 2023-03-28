package com.franchise.data.dtos.request;

import com.franchise.data.models.CandidateNames;
import lombok.Data;

@Data
public class VoteRequest {
    private CandidateNames yourFavoriteCandidate;
}
