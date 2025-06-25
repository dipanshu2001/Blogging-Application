package com.SpringProject.Blogging.Application.Services.UserServiceImplimentation;

import com.SpringProject.Blogging.Application.Exceptions.ResourceNotFoundException;
import com.SpringProject.Blogging.Application.Models.User;
import com.SpringProject.Blogging.Application.Payloads.UserDTO;
import com.SpringProject.Blogging.Application.Repositories.UserRepo;
import com.SpringProject.Blogging.Application.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = this.dtoToUser(userDTO);
        User savedUser = this.userRepo.save(user);
        return this.userToDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, int userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setUserName(userDTO.getUserName());
        user.setEmailId(userDTO.getEmailId());
        user.setPassword(userDTO.getPassword());

        User updatedUser = this.userRepo.save(user);
        return this.userToDTO(updatedUser);
    }

    @Override
    public UserDTO getUserByID(int userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return this.userToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        return users.stream()
                .map(this::userToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(int userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        this.userRepo.delete(user);
    }

    // Helper method: Convert DTO to Entity
    public User dtoToUser(UserDTO userDTO) {
        User user = this.modelMapper.map(userDTO, User.class);
        // Don't set ID here; let DB auto-generate
       /*
       user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        user.setEmailId(userDTO.getEmailId());
        */
        return user;

    }

    // Helper method: Convert Entity to DTO
    public UserDTO userToDTO(User user) {
        UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

}
