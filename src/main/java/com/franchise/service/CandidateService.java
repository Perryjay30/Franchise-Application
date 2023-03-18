package com.franchise.service;

import com.franchise.data.dtos.request.CandidateRequest;
import com.franchise.data.models.Candidate;
import jakarta.mail.MessagingException;

import java.util.List;

public interface CandidateService {

    Candidate addCandidate(CandidateRequest candidateRequest);
    Candidate updateCandidateDetails(String id, CandidateRequest editCandidateRequest) throws MessagingException;

    void getCandidate(String candidateId) throws MessagingException;

    List<Candidate> viewAllCandidates();
}
