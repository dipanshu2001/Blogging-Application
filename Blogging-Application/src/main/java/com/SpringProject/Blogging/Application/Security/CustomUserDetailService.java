package com.SpringProject.Blogging.Application.Security;

import com.SpringProject.Blogging.Application.Models.User;
import com.SpringProject.Blogging.Application.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        return userRepo.findByEmailId(emailId).orElseThrow(()->new UsernameNotFoundException("User not found with email: "+emailId));
    }

}
