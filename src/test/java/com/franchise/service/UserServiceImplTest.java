package com.franchise.service;

import com.franchise.data.dtos.request.*;
import com.franchise.data.dtos.response.Feedback;
import com.franchise.data.dtos.response.Reply;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void testThatUserCanRegister() {
        RegistrationRequest regRequest = new RegistrationRequest();
        regRequest.setFirstName("Jesus");
        regRequest.setEmailAddress("mrjesus3003@gmail.com");
        regRequest.setLastName("Christ");
        regRequest.setPassword("Jesuschrist@33");
        String answer = userService.register(regRequest);
        assertEquals("Token successfully sent to your email. Please check.", answer);
    }

    @Test
    void testThatUserAccountIsCreatedAndVerified() {
        VerifyOtpRequest verifyOtpRequest = new VerifyOtpRequest();
        verifyOtpRequest.setEmailAddress("mrjesus3003@gmail.com");
        verifyOtpRequest.setToken("3133");
        Feedback feedback = userService.createAccount(verifyOtpRequest);
        assertEquals("Registration Successful", feedback.getMessage());
    }

    @Test
    void testThatUserCanLogin() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmailAddress("mrjesus3003@gmail.com");
        loginRequest.setPassword("Jesuschrist@33");
        Reply reply = userService.login(loginRequest);
        assertEquals("Login is successful", reply.getMessage());
    }

    @Test
    void testThatTheForgotPasswordMethodWorks() throws MessagingException {
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail("mrjesus3003@gmail.com");
        String reaction = userService.forgotPassword(forgotPasswordRequest);
        assertEquals("Token successfully sent to your email. Please check.", reaction);
    }

    @Test
    void testThatUserCanResetPassword() {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setToken("8085");
        resetPasswordRequest.setPassword("Jesusofnazareth!17");
        resetPasswordRequest.setConfirmPassword("Jesusofnazareth!17");
        Reply resp = userService.resetPassword("mrjesus3003@gmail.com", resetPasswordRequest);
        assertEquals("Your password has been reset successfully", resp.getMessage());
    }

    @Test
    void testThatUserCanChangePassword() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("Jesusofnazareth!17");
        changePasswordRequest.setNewPassword("Jesusislord#247");
        Reply response = userService.changePassword("mrjesus3003@gmail.com", changePasswordRequest);
        assertEquals("Your password has been successfully changed", response.getMessage());
    }

    @Test
    void testThatUserDetailsCanBeUpdated() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setPhoneNumber("+419 (314) 435-8905");
        updateUserRequest.setStaffId("ZNA37O6E");
        Reply replyMessage = userService.updateUser("mrjesus3003@gmail.com", updateUserRequest);
        assertEquals("User details updated successfully", replyMessage.getMessage());
    }

    @Test
    void testThatUserCanBeDeleted() {
        DeleteUserRequest deleteRequest = new DeleteUserRequest();
        deleteRequest.setPassword("Jesusislord#247");
        String reaction = userService.deleteUser("mrjesus3003@gmail.com", deleteRequest);
        assertEquals("User deleted successfully", reaction);
    }
}
