package com.ashish.blog.services;

import java.util.List;

import com.ashish.blog.entities.User;
import com.ashish.blog.peyloads.UserDto;

public interface UserService {

	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user, Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);
	
	
	
	
}
