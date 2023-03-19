package com.franchise.service;

import com.franchise.data.dtos.request.UpdateElectionRequest;
import com.franchise.data.dtos.response.Reply;
import com.franchise.data.models.CreateElectionRequest;
import com.franchise.data.models.Election;
import jakarta.mail.MessagingException;

public interface ElectionService {

    Reply createElection(String adminId, CreateElectionRequest createElectionRequest);
    Reply updateElection(String adminId, String electionId, String candidateId, UpdateElectionRequest updateElectionRequest, Election updateElection) throws MessagingException;
    Object viewElection(String adminId, String electionId);
    void viewAllElections(String adminId);



}
