package com.franchise.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class Election {
    @Id
    private String electionId;
    private LocalDate electionDate;
    private ElectionType electionType;
    @DBRef
    private List<Candidate> candidateList = new ArrayList<>();

}
