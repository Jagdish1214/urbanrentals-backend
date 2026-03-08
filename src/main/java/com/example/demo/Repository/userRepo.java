package com.example.demo.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Dto.RegResponseDto;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.Users;

@Repository
public interface userRepo extends JpaRepository<Users,Integer> {
	
	Optional<Users> findByEmail(String email);
	Optional<Users> findById(Integer id);
	Object findByEmailAndPassword(String email, String password);
	
	List<Users> findByRole(Role role);

}
