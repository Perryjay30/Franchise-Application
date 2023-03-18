package com.franchise.service;

import com.franchise.data.dtos.request.CandidateRequest;
import com.franchise.data.dtos.request.UpdateElectionRequest;
import com.franchise.data.dtos.response.Reply;
import com.franchise.data.models.Candidate;
import com.franchise.data.models.CreateElectionRequest;
import com.franchise.data.models.Election;
import com.franchise.data.repositories.ElectionRepository;
import com.franchise.utils.exceptions.FranchiseException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;

    private final CandidateService candidateService;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    public ElectionServiceImpl(ElectionRepository electionRepository, CandidateService candidateService) {
        this.electionRepository = electionRepository;
        this.candidateService = candidateService;
    }

    @Override
    public Reply createElection(CreateElectionRequest createElectionRequest) {
        Election election = new Election();
        election.setElectionDate(LocalDateTime.from(LocalDate.parse(createElectionRequest.getElectionDate(), dateTimeFormatter)));
        election.setElectionType(createElectionRequest.getElectionType());
        CandidateRequest candidateRequest = new CandidateRequest();
        election.getCandidateList().add(candidateService.addCandidate(candidateRequest));
        electionRepository.save(election);
        return new Reply("Election created successfully");
    }

    @Override
    public Reply updateElection(String electionId, String candidateId, UpdateElectionRequest updateElectionRequest) throws MessagingException {
        Election createdElection = electionRepository.findById(electionId)
                .orElseThrow(() -> new FranchiseException("Election doesn't exist"));
        createdElection.setElectionType(updateElectionRequest.getElectionType() != null ?
                updateElectionRequest.getElectionType() : createdElection.getElectionType());
        createdElection.setElectionDate(updateElectionRequest.getElectionDate() != null && !updateElectionRequest.getElectionDate().equals("") ?
                LocalDateTime.parse(updateElectionRequest.getElectionDate()) : createdElection.getElectionDate());
        CandidateRequest candidateRequest = new CandidateRequest();
        createdElection.getCandidateList().add(candidateService.updateCandidateDetails(candidateId, candidateRequest));
        return new Reply("Election updated");
    }


    @Override
    public Candidate viewCandidate(String candidateId) {
        return null;
    }

    @Override
    public List<Candidate> viewAllCandidates() {
        return null;
    }
}
