package com.SpringProject.Blogging.Application.Services;

import com.SpringProject.Blogging.Application.Models.User;
import com.SpringProject.Blogging.Application.Payloads.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO user);

    UserDTO updateUser(UserDTO user, int user_Id);

    UserDTO getUserByID(int user_Id);

    List<UserDTO> getAllUsers();

    void deleteUser(int user_Id);
}
