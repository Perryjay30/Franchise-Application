package com.franchise.service;

import com.franchise.data.dtos.request.DeleteUserRequest;
import com.franchise.data.dtos.request.UpdateAdminRequest;
import com.franchise.data.dtos.response.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;

    @Test
    void testThatAdminCanUpdateDetails() {
        UpdateAdminRequest updateAdminRequest = new UpdateAdminRequest();
        updateAdminRequest.setUniqueAdminId("D1N9MW013");
        Reply reply  = adminService.updateAdminDetails("mrjesus3003@gmail.com", updateAdminRequest);
        assertEquals("Details updated successfully. You're now an admin!!", reply.getMessage());
    }

    @Test
    void testThatAdminCanBeDeleted() {
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setPassword("Jesusislord#247");
        String response = adminService.deleteAdmin("641606d2d6e4370cb21fb3b6", deleteUserRequest);
        assertEquals("User deleted successfully", response);
    }



}