package com.franchise.service;

import com.franchise.data.dtos.request.CandidateRequest;
import com.franchise.data.dtos.request.UpdateElectionRequest;
import com.franchise.data.dtos.response.Reply;
import com.franchise.data.models.CreateElectionRequest;
import com.franchise.data.models.Election;
import com.franchise.data.models.ElectionType;
import com.franchise.utils.exceptions.FranchiseException;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ElectionServiceImplTest {

    @Autowired
    private ElectionService electionService;

    private CreateElectionRequest newElection;

    private CandidateRequest candidateRequest;

    private CandidateRequest candidateRequest2;

    private CandidateRequest candidateRequest3;

    @BeforeEach
    void startsWithThis() {
        newElection = new CreateElectionRequest();
        newElection.setElectionDate("18/03/2023");
        newElection.setElectionType(ElectionType.MANAGING_DIRECTOR);
        candidateRequest = new CandidateRequest();
        candidateRequest.setFirstName("Daniel");
        candidateRequest.setLastName("Levy");
        candidateRequest.setAspiringPosition("Managing-director");
        candidateRequest2 = new CandidateRequest();
        candidateRequest2.setFirstName("David");
        candidateRequest2.setLastName("Stonebridge");
        candidateRequest2.setAspiringPosition("Managing-director");
        candidateRequest3 = new CandidateRequest();
        candidateRequest3.setFirstName("Micheal");
        candidateRequest3.setLastName("Ramadan");
        candidateRequest3.setAspiringPosition("Managing-director");
    }

    @Test
    void testThatElectionCanBeCreated() {
        newElection.getCandidateRequest().add(candidateRequest);
        newElection.getCandidateRequest().add(candidateRequest2);
        newElection.getCandidateRequest().add(candidateRequest3);
        Reply response = electionService.createElection("641608edf63cfa193bb4619a", newElection);
        assertEquals("Election created successfully", response.getMessage());
    }

    @Test
    void testThatElectionCanBeCreatedThrowsException() {
        newElection.getCandidateRequest().add(candidateRequest);
        assertThrows(FranchiseException.class, () -> electionService.createElection("641608edf63cfa193bb4619a", newElection));
    }

    @Test
    void testThatElectionCanBeUpdated() throws MessagingException {
        UpdateElectionRequest editElection = new UpdateElectionRequest();
        editElection.setElectionDate("30/03/2023");
        editElection.setElectionType(ElectionType.CHIEF_EXECUTIVE_OFFICER);
        CandidateRequest editCandidate = new CandidateRequest();
        editCandidate.setFirstName("Hamza");
        editCandidate.setLastName("Wayne");
        editCandidate.setAspiringPosition("Chief Executive Officer");
        editElection.getUpdateCandidateRequest().add(editCandidate);
        Reply resp = electionService.updateElection("641608edf63cfa193bb4619a", "64161abf0cf7432cfd024547", "64162ae17072b221ca6d3ecd", editElection, new Election());
    }


}