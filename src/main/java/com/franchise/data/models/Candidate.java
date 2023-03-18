package com.franchise.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@RequiredArgsConstructor
public class Candidate {
    @Id
    private String candidateId;
    private String firstName;
    private String lastName;
    private String aspiringPosition;

    public Candidate(String firstName, String lastName, String aspiringPosition) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.aspiringPosition = aspiringPosition;
    }
}
