package com.franchise.service;

import com.franchise.data.dtos.request.*;
import com.franchise.data.dtos.response.Feedback;
import com.franchise.data.dtos.response.Reply;
import jakarta.mail.MessagingException;

public interface UserService {
    String register(RegistrationRequest regRequest);
    Feedback createAccount(VerifyOtpRequest otpTokenRequest);
    String sendOTP(SendOtpRequest sendOtpRequest);

    void verifyOtp(VerifyOtpRequest verifyOtpRequest);
    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws MessagingException;

    Reply resetPassword(String emailAddress, ResetPasswordRequest resetPasswordRequest);

    String changePassword(String emailAddress, ChangePasswordRequest changePasswordRequest);

    Reply login(LoginRequest loginRequest);

    Reply updateUser(String emailAddress, UpdateUserRequest updateUserRequest);

    String deleteUser(String emailAddress, DeleteUserRequest deleteRequest);
}
