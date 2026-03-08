package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Dto.CarRequestDto;
import com.example.demo.Dto.CarResponseDto;
import com.example.demo.Entity.Cars;
import com.example.demo.Service.carService;

import jakarta.servlet.http.HttpSession;


@RequestMapping("/cars")
@RestController
public class CarsController {
	
	@Autowired
	carService cs;
	
	@PostMapping(value = "/add-car", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addCar(
	        @RequestPart("car") CarRequestDto dto,
	        @RequestPart("image") MultipartFile image,
	        HttpSession session) {

	    Integer ownerId = (Integer) session.getAttribute("USER_ID");

	    if (ownerId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    return ResponseEntity.ok(cs.addCar(dto, image, ownerId));
	}

	
	@GetMapping("/allcars")
	List<CarResponseDto> ShowAllCars(){
		return cs.getAllCars();
	}
	
	@GetMapping("/getCar/{id}")
	CarResponseDto GetCarById(@PathVariable Integer id) {
		return cs.getCarById(id);
	}
	
	@GetMapping("/search")
    public List<CarResponseDto> searchCars(
            @RequestParam String keyword
    ) {
        return cs.searchCars(keyword);
    }
	
	 @GetMapping("/Search")
	    public List<Cars> searchCars(
	            @RequestParam String location,
	            @RequestParam String pickupDate,
	            @RequestParam String returnDate) {

	        return cs.SearchCars(location, pickupDate, returnDate);
	    }
	
	@GetMapping("/owner/my_cars")
	public ResponseEntity<List<CarResponseDto>> getMyCars(HttpSession session) {

	    Integer userId = (Integer) session.getAttribute("USER_ID");

	    if (userId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    return ResponseEntity.ok(cs.getCarsAddedByUser(userId));
	}
	
	
	@PutMapping("/update-car/{id}")
	public ResponseEntity<String> updateCar(
	        @PathVariable Integer id,
	        @RequestPart("car") CarRequestDto dto,
	        @RequestParam(value = "image", required = false) MultipartFile image,
	        HttpSession session) {

	    Integer ownerId = (Integer) session.getAttribute("USER_ID");

	    if (ownerId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required");
	    }

	    try {
	        String result = cs.updateCar(id, dto, image, ownerId);
	        return ResponseEntity.ok(result);
	    } catch (Exception e) {
	        e.printStackTrace(); // 🔍 See exact reason for 500 error
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Failed to update car: " + e.getMessage());
	    }
	}

	
	@PutMapping("/update-carstatus/{id}/status")
	public ResponseEntity<String> updateStatus(
	        @PathVariable Integer id,
	        @RequestParam Boolean status,
	        HttpSession session) {

	    Integer ownerId = (Integer) session.getAttribute("USER_ID");

	    if (ownerId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required");
	    }

	    return ResponseEntity.ok(
	            cs.updateCarStatus(id, status, ownerId));
	}

	@DeleteMapping("/delete-Car/{id}")
	public ResponseEntity<String> deleteCar(
	        @PathVariable Integer id,
	        HttpSession session) {

	    Integer ownerId = (Integer) session.getAttribute("USER_ID");

	    if (ownerId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required");
	    }

	    return ResponseEntity.ok(
	            cs.deleteCar(id, ownerId));
	}




	
	
}
