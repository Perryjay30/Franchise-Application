package com.franchise.service;

import com.franchise.data.dtos.request.UpdateElectionRequest;
import com.franchise.data.dtos.request.VoteRequest;
import com.franchise.data.dtos.response.Reply;
import com.franchise.data.dtos.request.CreateElectionRequest;
import com.franchise.data.models.Election;
import com.franchise.data.models.Votes;
import jakarta.mail.MessagingException;

import java.util.List;

public interface ElectionService {

    Reply createElection(String adminId, CreateElectionRequest createElectionRequest);
    Reply updateElection(String adminId, String electionId, String candidateId, UpdateElectionRequest updateElectionRequest, Election updateElection) throws MessagingException;
    Object viewElection(String adminId, String electionId);
    Object viewAllElections(String adminId);
    List<Votes> getAllVotes();

    List<Votes> totalVotesPerCandidate(String adminId, VoteRequest voteRequest);
//    List<Votes> totalVotesOfDanielLevy(String adminId);
//    List<Votes> totalVotesOfDavidStoneBridge(String adminId);
//    List<Votes> totalVotesOfMichealRamadan(String adminId);



}
