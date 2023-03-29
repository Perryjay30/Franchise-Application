package com.franchise.controller;

import com.franchise.data.dtos.request.UpdateElectionRequest;
import com.franchise.data.dtos.request.VoteRequest;
import com.franchise.data.dtos.response.ApiResponse;
import com.franchise.data.dtos.request.CreateElectionRequest;
import com.franchise.data.models.Election;
import com.franchise.service.ElectionService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("franchise/polls")
@CrossOrigin(origins = "*")
public class ElectionController {

    private final ElectionService electionService;

    @Autowired
    public ElectionController(ElectionService electionService) {
        this.electionService = electionService;
    }

    @PostMapping("/createElection/{adminId}")
    public ResponseEntity<?> createElection(@Valid @PathVariable @RequestBody String adminId, CreateElectionRequest createElectionRequest, HttpServletRequest httpServletRequest) {
        ApiResponse response = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .statusCode(HttpStatus.OK)
                .path(httpServletRequest.getRequestURI())
                .data(electionService.createElection(adminId, createElectionRequest))
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/updateElection/{adminId}/{electionId}/{candidateId}")
    public ResponseEntity<?> updateElectionDetails(@Valid @RequestBody @PathVariable String adminId, String electionId, @PathVariable String candidateId,
                                                   UpdateElectionRequest updateElectionRequest, Election updateElection, HttpServletRequest httpServletRequest) throws MessagingException {
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .statusCode(HttpStatus.OK)
                .path(httpServletRequest.getRequestURI())
                .data(electionService.updateElection(adminId, electionId, candidateId, updateElectionRequest, updateElection))
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("viewElection/{adminId}/{electionId}")
    public ResponseEntity<?> viewElection(@PathVariable String adminId, @PathVariable String electionId, HttpServletRequest httpServletRequest) {
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .statusCode(HttpStatus.OK)
                .path(httpServletRequest.getRequestURI())
                .data(electionService.viewElection(adminId, electionId))
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("viewAllElection/{adminId}")
    public ResponseEntity<?> viewAllElections(@PathVariable String adminId, HttpServletRequest httpServletRequest) {
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .statusCode(HttpStatus.OK)
                .path(httpServletRequest.getRequestURI())
                .data(electionService.viewAllElections(adminId))
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("firstCandidateResult/{adminId}")
    public ResponseEntity<?> viewFirstCandidateResult(@PathVariable String adminId,
                                         HttpServletRequest httpServletRequest) {
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .statusCode(HttpStatus.OK)
                .path(httpServletRequest.getRequestURI())
                .data(electionService.totalVotesOfFirstCandidate(adminId))
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("secondCandidateResult/{adminId}")
    public ResponseEntity<?> viewSecondCandidateResult(@PathVariable String adminId,
                                         HttpServletRequest httpServletRequest) {
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .statusCode(HttpStatus.OK)
                .path(httpServletRequest.getRequestURI())
                .data(electionService.totalVotesOfSecondCandidate(adminId))
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("thirdCandidateResult/{adminId}")
    public ResponseEntity<?> viewThirdCandidateResult(@PathVariable String adminId,
                                                       HttpServletRequest httpServletRequest) {
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .statusCode(HttpStatus.OK)
                .path(httpServletRequest.getRequestURI())
                .data(electionService.totalVotesOfThirdCandidate(adminId))
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
