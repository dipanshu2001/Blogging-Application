package com.SpringProject.Blogging.Application;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BloggingApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BloggingApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper= new ModelMapper();
		mapper.typeMap(
				com.SpringProject.Blogging.Application.Models.User.class,
				com.SpringProject.Blogging.Application.Payloads.UserDTO.class
		).addMappings(m -> m.skip(com.SpringProject.Blogging.Application.Payloads.UserDTO::setRoles));

		return mapper;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("wow+$"));
		System.out.println(passwordEncoder.matches("wow+$", "$2a$10$tEbQ6PQ3MpYZ9E9Wd1/j.uEd1KGL4p.Eo77/2zx14VPbh3slnJOnq"));
	}
}