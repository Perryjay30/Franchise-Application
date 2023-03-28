package com.franchise.controller;

import com.franchise.data.dtos.request.DeleteUserRequest;
import com.franchise.data.dtos.request.UpdateAdminRequest;
import com.franchise.data.dtos.response.ApiResponse;
import com.franchise.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("franchise/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PatchMapping("/updateAdminDetails/{emailAddress}")
    public ResponseEntity<?> updateAdminDetails(@PathVariable @RequestBody @Valid String emailAddress,
                                                UpdateAdminRequest updateAdminRequest, HttpServletRequest httpServletRequest) {
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(adminService.updateAdminDetails(emailAddress, updateAdminRequest))
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK)
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAdmin/{adminId}")
    public ResponseEntity<?> deleteAdmin(@PathVariable @Valid @RequestBody String adminId, DeleteUserRequest deleteUserRequest,
                                         HttpServletRequest httpServletRequest) {
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK)
                .path(httpServletRequest.getRequestURI())
                .data(adminService.deleteAdmin(adminId, deleteUserRequest))
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("viewCandidate/{adminId}/{candidateId}")
    public ResponseEntity<?> viewElection(@PathVariable String adminId, @PathVariable String candidateId, HttpServletRequest httpServletRequest) {
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .statusCode(HttpStatus.OK)
                .path(httpServletRequest.getRequestURI())
                .data(adminService.viewCandidate(adminId, candidateId))
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("viewAllCandidate/{adminId}")
    public ResponseEntity<?> viewAllElections(@PathVariable String adminId, HttpServletRequest httpServletRequest) {
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .statusCode(HttpStatus.OK)
                .path(httpServletRequest.getRequestURI())
                .data(adminService.viewAllCandidates(adminId))
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
