package com.franchise.service;

import com.franchise.data.dtos.request.ChangePasswordRequest;
import com.franchise.data.dtos.request.DeleteUserRequest;
import com.franchise.data.dtos.request.UpdateAdminRequest;
import com.franchise.data.dtos.response.Reply;
import com.franchise.data.models.Admin;
import com.franchise.data.models.Role;
import com.franchise.data.models.User;
import com.franchise.data.repositories.AdminRepository;
import com.franchise.data.repositories.UserRepository;
import com.franchise.utils.exceptions.FranchiseException;
import com.franchise.utils.exceptions.UserServiceException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final AdminRepository adminRepository;

    private final UserService userService;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository, AdminRepository adminRepository, UserService userService) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.userService = userService;
    }

    @Override
    public Reply updateAdminDetails(String emailAddress, UpdateAdminRequest updateAdminRequest) {
        User existingUser = userRepository.findByEmailAddress(emailAddress)
                .orElseThrow(() -> new FranchiseException("This user isn't registered"));
        Admin registeredAdmin = new Admin();
        registeredAdmin.setEmailAddress(updateAdminRequest.getEmailAddress() != null && !updateAdminRequest.getEmailAddress()
                .equals("") ? updateAdminRequest.getEmailAddress() : existingUser.getEmailAddress());
        registeredAdmin.setStaffId(updateAdminRequest.getStaffId() != null && !updateAdminRequest.getStaffId().equals("")
                ? updateAdminRequest.getStaffId() : existingUser.getStaffId());
        registeredAdmin.setStaffId(updateAdminRequest.getStaffId() != null && !updateAdminRequest.getStaffId().equals("")
                ? updateAdminRequest.getStaffId() : existingUser.getStaffId());
        registeredAdmin.setLastName(updateAdminRequest.getLastName() != null && !updateAdminRequest.getLastName().equals("")
                ? updateAdminRequest.getLastName() : existingUser.getLastName());
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        registeredAdmin.setPassword(changePassword(emailAddress, changePasswordRequest) != null && !changePassword(emailAddress,
                changePasswordRequest).equals("") ? changePassword(emailAddress, changePasswordRequest) : existingUser.getPassword());
        if(userRepository.findByPhoneNumber(updateAdminRequest.getPhoneNumber()).isPresent()) {
            throw new UserServiceException("This phone number already exists, kindly use another!!");
        } else {
            registeredAdmin.setPhoneNumber(updateAdminRequest.getPhoneNumber() != null && !updateAdminRequest.getPhoneNumber().equals("")
                    ? updateAdminRequest.getPhoneNumber() : registeredAdmin.getPhoneNumber());
        }
        registeredAdmin.setFirstName(updateAdminRequest.getFirstName() != null && !updateAdminRequest.getFirstName().equals("")
                ? updateAdminRequest.getFirstName() : existingUser.getFirstName());
        registeredAdmin.setUniqueAdminId(updateAdminRequest.getUniqueAdminId() != null && !updateAdminRequest.getUniqueAdminId().equals("")
                ? updateAdminRequest.getUniqueAdminId() : registeredAdmin.getUniqueAdminId());
        registeredAdmin.setUserRole(Role.ADMIN);
        adminRepository.save(registeredAdmin);
        return new Reply("Details updated successfully. You're now an admin!!");
    }

    private String changePassword(String emailAddress, ChangePasswordRequest changePasswordRequest) {
        User verifiedUser = findUser(emailAddress);
        if (BCrypt.checkpw(changePasswordRequest.getOldPassword(), verifiedUser.getPassword()))
            verifiedUser.setPassword(changePasswordRequest.getNewPassword());
        userRepository.save(verifiedUser);
//        return "Your password has been successfully changed";
        System.out.println("Your password has been successfully changed");
        return verifiedUser.getPassword();
    }

    private User findUser(String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress)
                .orElseThrow(() -> new UserServiceException("This email isn't registered"));
    }

    @Override
    public String deleteAdmin(String adminId, DeleteUserRequest deleteRequest) {
        Admin findAdmin = adminRepository.findById(adminId).orElseThrow(()
                -> new UserServiceException("Admin is not registered"));
        if(BCrypt.checkpw(deleteRequest.getPassword(), findAdmin.getPassword())) {
            findAdmin.setEmailAddress("Deactivated" + " " + findAdmin.getEmailAddress());
            adminRepository.save(findAdmin);
        }
        return "User deleted successfully";
    }
}
