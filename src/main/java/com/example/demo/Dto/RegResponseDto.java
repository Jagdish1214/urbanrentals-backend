package com.example.demo.Dto;

import com.example.demo.Entity.Role;

public class RegResponseDto {

	private String message;
	private Integer id;
    private String name;
    private String email;
    private String password;
    private Role role;

    

	public RegResponseDto(String message,Integer id, String name, String email, Role role) {
		super();
		this.message = message;
		this.id=id;
		this.name = name;
		this.email = email;
		this.role = role;
	}


	
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public Role getRole() {
		return role;
	}



	public void setRole(Role role) {
		this.role = role;
	}

	
	

    
}
