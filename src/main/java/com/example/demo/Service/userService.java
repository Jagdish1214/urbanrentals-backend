package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Dto.LoginRequestDto;
import com.example.demo.Dto.LoginResponseDto;
import com.example.demo.Dto.RegResponseDto;
import com.example.demo.Dto.RegisterRequestDto;
import com.example.demo.Entity.Users;
import com.example.demo.Repository.userRepo;

import jakarta.servlet.http.HttpSession;


@Service
public class userService {
	
	@Autowired
	userRepo ur;

	
	 public RegResponseDto registration(RegisterRequestDto dto) {

	        Users u = new Users();
	        u.setName(dto.getName());
	        u.setEmail(dto.getEmail());
	        u.setPassword(dto.getPassword());
	        u.setRole(dto.getRole());

	        Users savedUser = ur.save(u);

	        return new RegResponseDto("User register Successfully!",
	        		savedUser.getId(),
	        		savedUser.getName() ,
	        		savedUser.getEmail(),
	        		savedUser.getRole());
	    }
	 
	 
	 
	 @Transactional
	    public LoginResponseDto login(LoginRequestDto dto, HttpSession session) {
	        // 1. Find user by email
	        Users user = ur.findByEmail(dto.getEmail())
	                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

	        // 2. Check password
	        if (!user.getPassword().equals(dto.getPassword())) {
	            throw new RuntimeException("Invalid email or password");
	        }

	        // 3. Store in session
	        session.setAttribute("USER_ID", user.getId());
	        session.setAttribute("ROLE", user.getRole());

	        // 4. Return response DTO
	        return new LoginResponseDto(
	                user.getId(),
	                user.getName(),
	                user.getEmail(),
	                user.getRole()
	        );
	    }
	 
	    public String logout(HttpSession session) {
	        session.invalidate();
	        return "Logged out successfully";
	    }

	    public List<Users> showUsers() {
	        return ur.findAll();
	    }
	 
	    public RegResponseDto getLoggedInUser(Integer userId) {

	        Users user = ur.findById(userId)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        return new RegResponseDto(
	                "",
	                user.getId(),
	                user.getName(),
	                user.getEmail(),
	                user.getRole()
	        );
	    }
	 
	 
	 
//	 public RegResponseDto login(LoginRequestDto dto,) {
//
//	        Users user = ur.findByEmail(dto.getEmail())
//	        		.orElseThrow(() -> new RuntimeException("User not found"));
//	        
//	        if(!user.getPassword().equals(dto.getPassword())) {
//	        	throw new RuntimeException("Invalid password");
//	        }
//	        
//	        if (!user.getRole().equals(dto.getRole())) {
//	            throw new RuntimeException("Role mismatch");
//	        }
//	        
//	        return new RegResponseDto("Login successful",
//	        		user.getName(), 
//	        		user.getEmail(), 
//	        		user.getPassword(), 
//	        		user.getRole());
//	        
//	        
//	    }
	 
	 
	
	public List<Users> addUsers(List<Users> li){
		return ur.saveAll(li);
	}
	
	
	
	
	
	
}
