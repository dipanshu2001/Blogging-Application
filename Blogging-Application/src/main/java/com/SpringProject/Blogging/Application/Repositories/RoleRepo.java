package com.SpringProject.Blogging.Application.Repositories;

import com.SpringProject.Blogging.Application.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(String name);
}
