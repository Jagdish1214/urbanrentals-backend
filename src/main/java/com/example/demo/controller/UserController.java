package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.LoginRequestDto;
import com.example.demo.Dto.LoginResponseDto;
import com.example.demo.Dto.RegResponseDto;
import com.example.demo.Dto.RegisterRequestDto;
import com.example.demo.Entity.Users;
import com.example.demo.Service.userService;

import jakarta.servlet.http.HttpSession;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	userService us;
	
	@PostMapping("/register")
	RegResponseDto AddUser(@RequestBody  RegisterRequestDto dto) {
		return us.registration(dto);
	}
	
	@PostMapping("/addAllUser")
	List<Users> AddlistofUsers(@RequestBody List<Users> li){
		return us.addUsers(li);
	}
	
	@PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto, HttpSession session) {
        LoginResponseDto response = us.login(dto, session);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        return us.logout(session);
    }
	
	@GetMapping
	List<Users> ShowAllUsers(){
		return us.showUsers();
	}
	
	@GetMapping("/me")
    public ResponseEntity<?> getLoggedInUser(HttpSession session) {

        Integer userId = (Integer) session.getAttribute("USER_ID");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(
                us.getLoggedInUser(userId)
        );
    }

	
	
}
