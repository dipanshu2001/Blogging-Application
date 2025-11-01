package com.SpringProject.Blogging.Application.Services.UserServiceImplimentation;

import com.SpringProject.Blogging.Application.Exceptions.ResourceNotFoundException;
import com.SpringProject.Blogging.Application.Models.Role;
import com.SpringProject.Blogging.Application.Models.User;
import com.SpringProject.Blogging.Application.Payloads.UserDTO;
import com.SpringProject.Blogging.Application.Repositories.RoleRepo;
import com.SpringProject.Blogging.Application.Repositories.UserRepo;
import com.SpringProject.Blogging.Application.Services.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired private UserRepo userRepo;
    @Autowired private RoleRepo roleRepo;
    @Autowired private ModelMapper modelMapper;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerNewUser(UserDTO userDTO) {

        User user = this.modelMapper.map(userDTO, User.class);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> assignedRoles = new HashSet<>();

        // ✅ Dynamic role assignment
        if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
            for (String roleName : userDTO.getRoles()) {
                Role role = roleRepo.findByName("ROLE_" + roleName.toUpperCase())
                        .orElseThrow(() -> new ResourceNotFoundException("Role", "name", roleName));

                assignedRoles.add(role);
            }
        } else {
            // ✅ Default role
            Role defaultRole = roleRepo.findByName("ROLE_USER")
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "name", "ROLE_USER"));
            assignedRoles.add(defaultRole);
        }

        user.setRoles(assignedRoles);

        User savedUser = userRepo.save(user);
        return userToDTO(savedUser);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = this.modelMapper.map(userDTO, User.class);
        User savedUser = userRepo.save(user);
        return userToDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, int userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", (long) userId));

        user.setUserName(userDTO.getUserName());
        user.setEmailId(userDTO.getEmailId());
        user.setPassword(userDTO.getPassword());

        User updatedUser = userRepo.save(user);
        return userToDTO(updatedUser);
    }

    @Override
    public UserDTO getUserByID(int userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", (long) userId));
        return userToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(this::userToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(int userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", (long) userId));
        userRepo.delete(user);
    }

    // ✅ Convert User → UserDTO (roles fixed)
    public UserDTO userToDTO(User user) {

        // ✅ Basic mapping
        UserDTO dto = modelMapper.map(user, UserDTO.class);

        // ✅ Ensure roles are never null
        Set<String> roleNames = new HashSet<>();

        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            roleNames = user.getRoles().stream()
                    .map(Role::getName)  // e.g., "ROLE_AUTHOR"
                    .collect(Collectors.toSet());
        }

        // ✅ Always set roles manually (never null)
        dto.setRoles(roleNames);

        return dto;
    }

}


