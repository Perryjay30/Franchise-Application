package com.franchise.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Votes {
    @Id
    private String votesId;
    private CandidateNames yourFavoriteCandidate;
}
