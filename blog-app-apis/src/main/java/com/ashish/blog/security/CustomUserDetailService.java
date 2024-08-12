package com.ashish.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ashish.blog.entities.User;
import com.ashish.blog.exceptions.ResourceNotFoundException;
import com.ashish.blog.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	 
	User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User","email: "+username,0));
		return user;
	}

}
