 package com.ashish.blog.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ashish.blog.peyloads.ApiResponse;
import com.ashish.blog.peyloads.UserDto;
import com.ashish.blog.services.UserService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5500")
public class UserController {

	@Autowired
	private UserService userService;
	
	// POST - Create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}
	
	// PUT - Update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable ("userId") Integer uid) {
		UserDto updatedUserDto = this.userService.updateUser(userDto, uid);
		return ResponseEntity.ok(updatedUserDto);
}
	
//	@PostMapping("/")
//	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
//	    UserDto createUserDto = this.userService.createUser(userDto);
//	    return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
//	}
//
//	@PutMapping("/{userId}")
//	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid) {
//	    UserDto updatedUserDto = this.userService.updateUser(userDto, uid);
//	    return ResponseEntity.ok(updatedUserDto);
//	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
	    this.userService.deleteUser(userId);
	    return new ResponseEntity<>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
	}
	
	// GET - Get user by ID
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer userId) {
	    UserDto userDto = this.userService.getUserById(userId);
	    return ResponseEntity.ok(userDto);
	}

	
	// GET - Get all users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		//List<UserDto> users = this.userService.getAllUsers();
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
}
