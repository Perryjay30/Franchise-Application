package com.franchise.service;

import com.franchise.data.dtos.request.DeleteUserRequest;
import com.franchise.data.dtos.request.UpdateAdminRequest;
import com.franchise.data.dtos.response.Reply;

public interface AdminService {

    Reply updateAdminDetails(String emailAddress, UpdateAdminRequest updateAdminRequest);

    String deleteAdmin(String adminId, DeleteUserRequest deleteUserRequest);

    Object viewCandidate(String adminId, String candidateId);

    Object viewAllCandidates(String adminId);

    String deleteUserById(String adminId, String userId);

    String deleteAllUsers(String adminId, DeleteUserRequest deleteUserRequest);

}
