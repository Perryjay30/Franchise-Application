package com.franchise.service;

import com.franchise.utils.exceptions.FranchiseException;
import com.franchise.data.dtos.request.CandidateRequest;
import com.franchise.data.models.Candidate;
import com.franchise.data.repositories.AdminRepository;
import com.franchise.data.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    private final AdminRepository adminRepository;

    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository, AdminRepository adminRepository) {
        this.candidateRepository = candidateRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public Candidate addCandidate(CandidateRequest candidateRequest) {
//        Admin registeredAdmin = adminRepository.findById(adminId)
//                .orElseThrow(() -> new CandidateException("Admin isn't registered"));
        Candidate candidate = new Candidate(
                candidateRequest.getFirstName(),
                candidateRequest.getLastName(),
                candidateRequest.getAspiringPosition()
        );
        return candidateRepository.save(candidate);
    }


    @Override
    public Candidate updateCandidateDetails(String id, CandidateRequest editCandidateRequest)  {
        var foundCandidate = candidateRepository.findById(id).orElseThrow(()->
                new FranchiseException("Candidate with the id" +id+ "not found"));
        foundCandidate.setFirstName(editCandidateRequest.getFirstName() != null
                && !editCandidateRequest.getFirstName().equals("") ? editCandidateRequest.getFirstName() :
                foundCandidate.getFirstName());

        foundCandidate.setLastName(editCandidateRequest.getLastName() != null
                && !editCandidateRequest.getLastName().equals("") ? editCandidateRequest.getLastName() :
                foundCandidate.getLastName());

        foundCandidate.setAspiringPosition(editCandidateRequest.getAspiringPosition() != null
                && !editCandidateRequest.getAspiringPosition().equals("") ? editCandidateRequest.getAspiringPosition() :
                foundCandidate.getAspiringPosition());

        return candidateRepository.save(foundCandidate);
    }

    @Override
    public void getCandidate(String candidateId)  {
        candidateRepository.findById(candidateId).orElseThrow(()
                -> new FranchiseException("Candidate isn't registered"));
    }

    @Override
    public List<Candidate> viewAllCandidates() {
        return null;
    }
}
