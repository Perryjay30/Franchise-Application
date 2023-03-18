package com.franchise.service;

import com.franchise.data.dtos.request.CandidateRequest;
import com.franchise.data.dtos.request.UpdateElectionRequest;
import com.franchise.data.dtos.response.Reply;
import com.franchise.data.models.Admin;
import com.franchise.data.models.CreateElectionRequest;
import com.franchise.data.models.Election;
import com.franchise.data.repositories.AdminRepository;
import com.franchise.data.repositories.ElectionRepository;
import com.franchise.utils.exceptions.FranchiseException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;

    private final CandidateService candidateService;

    private final AdminRepository adminRepository;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    public ElectionServiceImpl(ElectionRepository electionRepository, CandidateService candidateService, AdminRepository adminRepository) {
        this.electionRepository = electionRepository;
        this.candidateService = candidateService;
        this.adminRepository = adminRepository;
    }

    @Override
    public Reply createElection(String adminId, CreateElectionRequest createElectionRequest) {
        Admin registeredAdmin = getRegisteredAdmin(adminId);
        Election election = new Election();
        election.setElectionDate(LocalDateTime.from(LocalDate.parse(createElectionRequest.getElectionDate(), dateTimeFormatter)));
        election.setElectionType(createElectionRequest.getElectionType());
        CandidateRequest candidateRequest = new CandidateRequest();
        election.getCandidateList().add(candidateService.addCandidate(candidateRequest));
        electionRepository.save(election);
        return new Reply("Election created successfully");
    }

    @Override
    public Reply updateElection(String adminId, String electionId, String candidateId, UpdateElectionRequest updateElectionRequest) throws MessagingException {
        Admin registeredAdmin = getRegisteredAdmin(adminId);
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
    public void viewElection(String adminId, String electionId) {
        Admin registeredAdmin = getRegisteredAdmin(adminId);
        electionRepository.findById(electionId);
    }

    private Admin getRegisteredAdmin(String adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new FranchiseException("Admin isn't registered"));
    }

    @Override
    public void viewAllElections(String adminId) {
        Admin registeredAdmin = getRegisteredAdmin(adminId);
        electionRepository.findAll();
    }

}
