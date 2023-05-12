package com.franchise.controller;

import com.franchise.data.dtos.request.*;
import com.franchise.data.dtos.response.ApiResponse;
import com.franchise.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("franchise/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest registrationRequest, HttpServletRequest httpServletRequest) {
        String registeringUser = userService.register(registrationRequest);
        ApiResponse apiResponse = ApiResponse.builder().
                statusCode(HttpStatus.OK).
                data(registeringUser).
                timeStamp(ZonedDateTime.now()).
                path(httpServletRequest.getRequestURI()).
                isSuccessful(true).
                build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/createAccount")
    public ResponseEntity<?> createAccount(@RequestBody @Valid VerifyOtpRequest verifyOtpRequest, HttpServletRequest httpServletRequest) {
        ApiResponse apiResponse = ApiResponse.builder().
                statusCode(HttpStatus.CREATED).
                data(userService.createAccount(verifyOtpRequest)).
                timeStamp(ZonedDateTime.now()).
                path(httpServletRequest.getRequestURI()).
                isSuccessful(true).
                build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest httpServletRequest) {
        ApiResponse apiResponse = ApiResponse.builder().
                statusCode(HttpStatus.OK).
                data(userService.login(loginRequest)).
                timeStamp(ZonedDateTime.now()).
                path(httpServletRequest.getRequestURI()).
                isSuccessful(true).
                build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest, HttpServletRequest httpServletRequest) throws MessagingException {
        ApiResponse apiResponse = ApiResponse.builder().
                statusCode(HttpStatus.OK).
                data(userService.forgotPassword(forgotPasswordRequest)).
                timeStamp(ZonedDateTime.now()).
                path(httpServletRequest.getRequestURI()).
                isSuccessful(true).
                build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/resetPassword/{emailAddress}")
    public ResponseEntity<?> resetPassword(@PathVariable @RequestBody @Valid String emailAddress, ResetPasswordRequest resetPasswordRequest, HttpServletRequest httpServletRequest)  {
        ApiResponse apiResponse = ApiResponse.builder().
                statusCode(HttpStatus.OK).
                data(userService.resetPassword(emailAddress, resetPasswordRequest)).
                timeStamp(ZonedDateTime.now()).
                path(httpServletRequest.getRequestURI()).
                isSuccessful(true).
                build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/changePassword/{emailAddress}")
    public ResponseEntity<?> changePassword(@PathVariable @RequestBody @Valid String emailAddress, ChangePasswordRequest changePasswordRequest, HttpServletRequest httpServletRequest)  {
        ApiResponse apiResponse = ApiResponse.builder().
                statusCode(HttpStatus.OK).
                data(userService.changePassword(emailAddress, changePasswordRequest)).
                timeStamp(ZonedDateTime.now()).
                path(httpServletRequest.getRequestURI()).
                isSuccessful(true).
                build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("/updateUser/{emailAddress}")
    public ResponseEntity<?> updateUser(@PathVariable @RequestBody @Valid String emailAddress, UpdateUserRequest updateUserRequest, HttpServletRequest httpServletRequest)  {
        ApiResponse apiResponse = ApiResponse.builder().
                statusCode(HttpStatus.OK).
                data(userService.updateUser(emailAddress, updateUserRequest)).
                timeStamp(ZonedDateTime.now()).
                path(httpServletRequest.getRequestURI()).
                isSuccessful(true).
                build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{emailAddress}")
    public ResponseEntity<?> deleteUser(@PathVariable @RequestBody @Valid String emailAddress, DeleteUserRequest deleteUserRequest, HttpServletRequest httpServletRequest)  {
        ApiResponse apiResponse = ApiResponse.builder().
                statusCode(HttpStatus.OK).
                data(userService.deleteUser(emailAddress, deleteUserRequest)).
                timeStamp(ZonedDateTime.now()).
                path(httpServletRequest.getRequestURI()).
                isSuccessful(true).
                build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/vote/{emailAddress}")
    public ResponseEntity<?> userCanVote(@PathVariable @RequestBody @Valid String emailAddress, VoteRequest voteRequest, HttpServletRequest httpServletRequest) {
        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.OK)
                .data(userService.vote(emailAddress, voteRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
