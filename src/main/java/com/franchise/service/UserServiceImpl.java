package com.franchise.service;

import com.franchise.data.dtos.request.*;
import com.franchise.data.dtos.response.Feedback;
import com.franchise.data.dtos.response.Reply;
import com.franchise.data.models.OtpToken;
import com.franchise.data.models.Status;
import com.franchise.data.models.User;
import com.franchise.data.repositories.OtpTokenRepository;
import com.franchise.data.repositories.UserRepository;
import com.franchise.utils.Token;
import com.franchise.utils.Validators;
import com.franchise.utils.exceptions.UserServiceException;
import jakarta.mail.MessagingException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final OtpTokenRepository otpTokenRepository;

    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, OtpTokenRepository otpTokenRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.otpTokenRepository = otpTokenRepository;
        this.emailService = emailService;
    }

    private User findUser(String emailAddress, String message) {
        return userRepository.findByEmailAddress(emailAddress)
                .orElseThrow(() -> new UserServiceException(message));
    }

    @Override
    public String register(RegistrationRequest regRequest) {
        if(!Validators.validateEmail(regRequest.getEmailAddress()))
            throw new UserServiceException("Email address is not valid");
        User user = new User();
        user.setFirstName(regRequest.getFirstName());
        user.setLastName(regRequest.getLastName());
        user.setPassword(regRequest.getPassword());
        if(userRepository.findByEmailAddress(regRequest.getEmailAddress()).isPresent())
            throw new UserServiceException("This email has been used by another user, kindly use another");
        else
            user.setEmailAddress(regRequest.getEmailAddress());
        user.setStatus(Status.UNVERIFIED);
        userRepository.save(user);
        SendOtpRequest sendOtpRequest = new SendOtpRequest();
        sendOtpRequest.setEmail(regRequest.getEmailAddress());
        return sendOTP(sendOtpRequest);
    }

    @Override
    public Feedback createAccount(VerifyOtpRequest otpTokenRequest) {
        verifyOtp(otpTokenRequest);
        User registeredUser = findUser(otpTokenRequest.getEmailAddress(), "Email not found");
        registeredUser.setStatus(Status.VERIFIED);
        userRepository.save(registeredUser);
        Feedback feedback = new Feedback();
        feedback.setUserId(registeredUser.getUserId());
        feedback.setStatusCode(201);
        feedback.setMessage("Registration Successful");
        return feedback;
    }

    @Override
    public String sendOTP(SendOtpRequest sendOtpRequest) {
        User savedUser = findUser(sendOtpRequest.getEmail(), "Email not found");
        return generateOtpToken(sendOtpRequest, savedUser);
    }

    @Override
    public void verifyOtp(VerifyOtpRequest verifyOtpRequest) {
        OtpToken foundToken = getFoundToken(verifyOtpRequest);
        if(foundToken.getExpiredAt().isBefore(LocalDateTime.now()))
            throw new UserServiceException("Token has expired");
        if(foundToken.getVerifiedAt() != null)
            throw new UserServiceException("Token has been used");
        if(!Objects.equals(verifyOtpRequest.getToken(), foundToken.getToken()))
            throw new UserServiceException("Incorrect token");
//        foundToken.setVerifiedAt(LocalDateTime.now());
        var token = getFoundToken(verifyOtpRequest);
        token.setVerifiedAt(LocalDateTime.now());
        otpTokenRepository.save(token);
    }

    private OtpToken getFoundToken(VerifyOtpRequest verifyOtpRequest) {
        return otpTokenRepository.findByToken(verifyOtpRequest.getToken())
                .orElseThrow(() -> new UserServiceException("Token doesn't exist"));
    }

    private String generateOtpToken(SendOtpRequest sendOtpRequest, User savedUser) {
        String token = Token.generateToken();
        OtpToken otpToken = new OtpToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                savedUser);
        otpTokenRepository.save(otpToken);
        emailService.send(sendOtpRequest.getEmail(), emailService.buildEmail(savedUser.getFirstName(), token));
        return "Token successfully sent to your email. Please check.";
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws MessagingException {
        User forgottenUser = findUser(forgotPasswordRequest.getEmail(), "This email isn't registered");
        String token = Token.generateToken();
        OtpToken otpToken = new OtpToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), forgottenUser);
        otpTokenRepository.save(otpToken);
        emailService.sendEmail(forgotPasswordRequest.getEmail(), forgottenUser.getFirstName(), token);
        return "Token successfully sent to your email. Please check.";
    }

    @Override
    public Reply resetPassword(String emailAddress, ResetPasswordRequest resetPasswordRequest) {
//        VerifyOtpRequest verifyOtpRequest= new VerifyOtpRequest();
//        verifyOtpRequest.setEmailAddress(emailAddress);
//        verifyOtp(verifyOtpRequest);
        verifyOtpForResetPassword(resetPasswordRequest);
        User foundUser = userRepository.findByEmailAddress(emailAddress).get();
        foundUser.setPassword(resetPasswordRequest.getPassword());
        if(BCrypt.checkpw(resetPasswordRequest.getConfirmPassword(), resetPasswordRequest.getPassword())) {
            userRepository.save(foundUser);
            return new Reply("Your password has been reset successfully");
        } else {
                throw new UserServiceException("Password does nt match");
        }
    }

    private void verifyOtpForResetPassword(ResetPasswordRequest resetPasswordRequest) {
        var foundToken = otpTokenRepository.findByToken(resetPasswordRequest.getToken())
                .orElseThrow(() -> new RuntimeException("Token doesn't exist"));
        if(foundToken.getVerifiedAt() != null)
            throw new RuntimeException("Token has been used");
        if(foundToken.getExpiredAt().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Token has expired");
        if(!Objects.equals(resetPasswordRequest.getToken(), foundToken.getToken()))
            throw new RuntimeException("Incorrect token");
        var token = otpTokenRepository.findByToken(foundToken.getToken())
                .orElseThrow(()->new RuntimeException("token not found"));
        token.setVerifiedAt(LocalDateTime.now());
        otpTokenRepository.save(token);
    }

    @Override
    public Reply changePassword(String emailAddress, ChangePasswordRequest changePasswordRequest) {
        User verifiedUser = findUser(emailAddress, "This email isn't registered");
        if(BCrypt.checkpw(changePasswordRequest.getOldPassword(), verifiedUser.getPassword()))
            verifiedUser.setPassword(changePasswordRequest.getNewPassword());
        userRepository.save(verifiedUser);
        return new Reply("Your password has been successfully changed");
    }

    @Override
    public Reply login(LoginRequest loginRequest) {
        User registeredUser = findUser(loginRequest.getEmailAddress(), "Email cannot be found");

        Reply loginResponse = new Reply();
//        if(registeredCustomer.getPassword().equals(loginRequest.getPassword())) {
        if(BCrypt.checkpw(loginRequest.getPassword(), registeredUser.getPassword())) {
            loginResponse.setMessage("Login is successful");
            return loginResponse;
        }
        else
            loginResponse.setMessage("Try again, Email or password is incorrect");
        return loginResponse;
    }

    @Override
    public Reply updateUser(String emailAddress, UpdateUserRequest updateUserRequest) {
        User updateUser = findUser(emailAddress, "This email isn't registered");
        updateUser.setFirstName(updateUserRequest.getFirstName() != null && !updateUserRequest.getFirstName()
                .equals("") ? updateUserRequest.getFirstName() : updateUser.getFirstName());
        if(userRepository.findByPhoneNumber(updateUserRequest.getPhoneNumber()).isPresent())
            throw new RuntimeException("This Phone Number has been taken, kindly use another");
        else
            updateUser.setPhoneNumber(updateUserRequest.getPhoneNumber() != null && !updateUserRequest.getPhoneNumber()
                    .equals("") ? updateUserRequest.getPhoneNumber() : updateUser.getPhoneNumber());
        updateUser.setLastName(updateUserRequest.getLastName() != null && !updateUserRequest.getLastName()
                .equals("") ? updateUserRequest.getLastName() : updateUser.getLastName());
        updateUser.setStaffId(updateUserRequest.getStaffId() != null && !updateUserRequest.getStaffId()
                .equals("") ? updateUserRequest.getStaffId() : updateUser.getStaffId());
//        updateUser.setEmailAddress(updateUser.getEmailAddress() != null && !updateUserRequest.getEmailAddress()
//                .equals("") ? updateUserRequest.getEmailAddress() : updateUser.getEmailAddress());
        userRepository.save(updateUser);
        return new Reply("User details updated successfully");
    }

    @Override
    public String deleteUser(String emailAddress, DeleteUserRequest deleteRequest) {
        System.out.println("Are you sure you want to delete your account?");
        User existingUser = findUser(emailAddress, "Incorrect url link");
        if(BCrypt.checkpw(deleteRequest.getPassword(), existingUser.getPassword())) {
            existingUser.setEmailAddress("Deactivated" + " " + existingUser.getEmailAddress());
            userRepository.save(existingUser);
        }
        return "User deleted successfully";
    }


}
