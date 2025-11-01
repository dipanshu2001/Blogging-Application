package com.SpringProject.Blogging.Application.Repositories;

import com.SpringProject.Blogging.Application.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
Optional<User> findByEmailId(String emailId);
}

