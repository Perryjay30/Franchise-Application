package com.franchise.service;

import com.franchise.data.dtos.request.CandidateRequest;
import com.franchise.data.dtos.request.UpdateElectionRequest;
import com.franchise.data.dtos.request.VoteRequest;
import com.franchise.data.dtos.response.Reply;
import com.franchise.data.models.Admin;
import com.franchise.data.dtos.request.CreateElectionRequest;
import com.franchise.data.models.CandidateNames;
import com.franchise.data.models.Election;
import com.franchise.data.models.Votes;
import com.franchise.data.repositories.AdminRepository;
import com.franchise.data.repositories.ElectionRepository;
import com.franchise.data.repositories.VotesRepository;
import com.franchise.utils.exceptions.FranchiseException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;

    private final CandidateService candidateService;

    private final AdminRepository adminRepository;

    private final VotesRepository votesRepository;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    public ElectionServiceImpl(ElectionRepository electionRepository, CandidateService candidateService, AdminRepository adminRepository, VotesRepository votesRepository) {
        this.electionRepository = electionRepository;
        this.candidateService = candidateService;
        this.adminRepository = adminRepository;
        this.votesRepository = votesRepository;
    }

    @Override
    public Reply createElection(String adminId, CreateElectionRequest createElectionRequest) {
        Admin registeredAdmin = getRegisteredAdmin(adminId);
        Election election = new Election();
        election.setElectionDate(LocalDate.from(LocalDate.parse(createElectionRequest.getElectionDate(), dateTimeFormatter)));
        election.setElectionType(createElectionRequest.getElectionType());
        addCandidateList(createElectionRequest, election);
        if (election.getCandidateList().size() < 2) throw new
                FranchiseException("The  minimum number of candidates is two");

        if (election.getElectionDate().isBefore(LocalDate.now())) throw new
                FranchiseException("The selected date has passed");
//        CandidateRequest candidateRequest = new CandidateRequest();
//        election.getCandidateList().add(candidateService.addCandidate(candidateRequest));
        electionRepository.save(election);
        return new Reply("Election created successfully");
    }

    private void addCandidateList(CreateElectionRequest createElectionRequest, Election election) {
        for (int i = 0; i < createElectionRequest.getCandidateRequest().size(); i++) {
            CandidateRequest request = new CandidateRequest();
            request.setFirstName((createElectionRequest.getCandidateRequest().get(i).getFirstName()));
            request.setLastName((createElectionRequest.getCandidateRequest().get(i).getLastName()));
            request.setAspiringPosition((createElectionRequest.getCandidateRequest().get(i).getAspiringPosition()));
            election.getCandidateList().add(candidateService.addCandidate(request));
        }
    }

    @Override
    public Reply updateElection(String adminId, String electionId, String candidateId, UpdateElectionRequest updateElectionRequest, Election updateElection) throws MessagingException {
        Admin registeredAdmin = getRegisteredAdmin(adminId);
        Election createdElection = electionRepository.findById(electionId)
                .orElseThrow(() -> new FranchiseException("Election doesn't exist"));
        createdElection.setElectionType(updateElectionRequest.getElectionType() != null ?
                updateElectionRequest.getElectionType() : createdElection.getElectionType());
        createdElection.setElectionDate(LocalDate.from(LocalDate.parse(updateElectionRequest.getElectionDate(), dateTimeFormatter) != null  ?
                LocalDate.from(LocalDate.parse(updateElectionRequest.getElectionDate(), dateTimeFormatter)) : createdElection.getElectionDate()));
        updateCandidateList(candidateId, updateElectionRequest, updateElection);
        if (createdElection.getCandidateList().size() < 2) throw new
                FranchiseException("The  minimum number of candidates is two");

        if (createdElection.getElectionDate().isBefore(LocalDate.now())) throw new
                FranchiseException("The selected date has passed");
        return new Reply("Election updated");
    }

    private void updateCandidateList(String candidateId, UpdateElectionRequest updateElectionRequest, Election updateElection) throws MessagingException {
        for (int i = 0; i < updateElectionRequest.getUpdateCandidateRequest().size(); i++) {
            CandidateRequest updateCandidateRequest = new CandidateRequest();
            updateCandidateRequest.setFirstName(updateElectionRequest.getUpdateCandidateRequest().get(i).getFirstName());
            updateCandidateRequest.setLastName(updateElectionRequest.getUpdateCandidateRequest().get(i).getLastName());
            updateCandidateRequest.setAspiringPosition(updateElectionRequest.getUpdateCandidateRequest().get(i).getAspiringPosition());
            updateElection.getCandidateList().add(candidateService.updateCandidateDetails(candidateId, updateCandidateRequest));
        }
    }


    @Override
    public Object viewElection(String adminId, String electionId) {
        Admin registeredAdmin = getRegisteredAdmin(adminId);
        return electionRepository.findById(electionId);

    }

    private Admin getRegisteredAdmin(String adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new FranchiseException("Admin isn't registered"));
    }

    @Override
    public Object viewAllElections(String adminId) {
        Admin registeredAdmin = getRegisteredAdmin(adminId);
        return electionRepository.findAll();
    }

    @Override
    public List<Votes> getAllVotes() {
        return votesRepository.findAll();
    }

    @Override
    public List<Votes> totalVotesPerCandidate(String adminId, VoteRequest voteRequest) {
        Admin registeredAdmin = getRegisteredAdmin(adminId);
        List<Votes> allVotes = getAllVotes();
        List<Votes> votesList = new ArrayList<>();
        Votes voting = new Votes();
        voting.setYourFavoriteCandidate(voteRequest.getYourFavoriteCandidate());
        for (Votes vote : allVotes)
            if(voteRequest.getYourFavoriteCandidate().equals(CandidateNames.DANIEL_LEVY))
                votesList.add(vote);
            else if(voteRequest.getYourFavoriteCandidate().equals(CandidateNames.MICHEAL_RAMADAN))
                votesList.add(vote);
            else if(voteRequest.getYourFavoriteCandidate().equals(CandidateNames.DAVID_STONEBRIDGE))
                votesList.add(vote);
        return votesList;
    }

//    @Override
//    public List<Votes> getElectionResult(String adminId) {
//
//    }


}
