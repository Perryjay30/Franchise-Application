package com.franchise.service;

import com.franchise.data.dtos.request.DeleteUserRequest;
import com.franchise.data.dtos.request.UpdateAdminRequest;
import com.franchise.data.dtos.response.Reply;

public interface AdminService {

    Reply updateAdminDetails(String emailAddress, UpdateAdminRequest updateAdminRequest);

    String deleteAdmin(String adminId, DeleteUserRequest deleteUserRequest);

}
