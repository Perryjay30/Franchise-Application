package com.franchise.service;

import com.franchise.data.dtos.request.UpdateElectionRequest;
import com.franchise.data.dtos.response.Reply;
import com.franchise.data.models.Candidate;
import com.franchise.data.models.CreateElectionRequest;
import jakarta.mail.MessagingException;

import java.util.List;

public interface ElectionService {

    Reply createElection(CreateElectionRequest createElectionRequest);
    Reply updateElection(String electionId, String candidateId, UpdateElectionRequest updateElectionRequest) throws MessagingException;
    Candidate viewCandidate(String candidateId);
    List<Candidate> viewAllCandidates();



}
