package com.franchise.service;

import com.franchise.data.dtos.request.ChangePasswordRequest;
import com.franchise.data.dtos.request.DeleteUserRequest;
import com.franchise.data.dtos.request.UpdateAdminRequest;
import com.franchise.data.dtos.response.Reply;
import com.franchise.data.models.Admin;
import com.franchise.data.models.Role;
import com.franchise.data.models.User;
import com.franchise.data.repositories.AdminRepository;
import com.franchise.data.repositories.CandidateRepository;
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

    private final CandidateRepository candidateRepository;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository, AdminRepository adminRepository, CandidateRepository candidateRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.candidateRepository = candidateRepository;
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
        registeredAdmin.setLastName(updateAdminRequest.getLastName() != null && !updateAdminRequest.getLastName().equals("")
                ? updateAdminRequest.getLastName() : existingUser.getLastName());
        updatingAdminDetails(emailAddress, updateAdminRequest, existingUser, registeredAdmin);
        adminRepository.save(registeredAdmin);
        return new Reply("Details updated successfully. You're now an admin!!");
    }

    private void updatingAdminDetails(String emailAddress, UpdateAdminRequest updateAdminRequest, User existingUser, Admin registeredAdmin) {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        registeredAdmin.setPassword(changePassword(emailAddress, changePasswordRequest) != null && !changePassword(emailAddress,
                changePasswordRequest).equals("") ? changePassword(emailAddress, changePasswordRequest) : existingUser.getPassword());
        registeredAdmin.setPhoneNumber(updateAdminRequest.getPhoneNumber() != null && !updateAdminRequest.getPhoneNumber().equals("")
                ? updateAdminRequest.getPhoneNumber() : registeredAdmin.getPhoneNumber());
        if(userRepository.findByPhoneNumber(updateAdminRequest.getPhoneNumber()).isPresent())
            throw new UserServiceException("This phone number already exists, kindly use another!!");
        registeredAdmin.setFirstName(updateAdminRequest.getFirstName() != null && !updateAdminRequest.getFirstName().equals("")
                ? updateAdminRequest.getFirstName() : existingUser.getFirstName());
        registeredAdmin.setUniqueAdminId(updateAdminRequest.getUniqueAdminId() != null && !updateAdminRequest.getUniqueAdminId().equals("")
                ? updateAdminRequest.getUniqueAdminId() : registeredAdmin.getUniqueAdminId());
        registeredAdmin.setUserRole(Role.ADMIN);
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
        Admin findAdmin = getAdmin(adminId);
        if(BCrypt.checkpw(deleteRequest.getPassword(), findAdmin.getPassword())) {
            findAdmin.setEmailAddress("Deactivated" + " " + findAdmin.getEmailAddress());
            adminRepository.save(findAdmin);
        }
        return "User deleted successfully";
    }

    private Admin getAdmin(String adminId) {
        return adminRepository.findById(adminId).orElseThrow(()
                -> new UserServiceException("Admin is not registered"));
    }

    @Override
    public Object viewCandidate(String adminId, String candidateId) {
        Admin registeredAdmin = getAdmin(adminId);
        return candidateRepository.findById(candidateId);
    }

    @Override
    public Object viewAllCandidates(String adminId) {
        Admin registeredAdmin = getAdmin(adminId);
        return candidateRepository.findAll();
    }

    @Override
    public String deleteUserById(String adminId, String userId) {
        Admin registeredAdmin = getAdmin(adminId);
        userRepository.deleteById(userId);
        return "User deleted successfully";
    }

    @Override
    public String deleteAllUsers(String adminId, DeleteUserRequest deleteUserRequest) {
//        Admin registeredAdmin = getAdmin(adminId);
//        String delete = "Deactivated User" + "  " +
//        if(deleteUserRequest.getPassword().equals(registeredAdmin.getPassword()))
//
        return null;
    }
}
