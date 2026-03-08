package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.BookingRequestDto;
import com.example.demo.Dto.BookingResponseDto;
import com.example.demo.Entity.BookingStatus;
import com.example.demo.Entity.Users;
import com.example.demo.Service.BookingService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/booking")
public class BookingController {
	
	@Autowired
	BookingService bs;
	
	@PostMapping("/Car_booking")
	public ResponseEntity<BookingResponseDto> createBooking(
	        @RequestBody @Valid BookingRequestDto dto,
	        HttpSession session
	) {
	    Integer userId = (Integer) session.getAttribute("USER_ID");

	    if (userId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .build();
	    }

	    BookingResponseDto response = bs.createBooking(dto, userId);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/booking_details")
	public ResponseEntity<List<BookingResponseDto>> getMyBookings(HttpSession session) {

	    Integer userId = (Integer) session.getAttribute("USER_ID");

	    if (userId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    List<BookingResponseDto> bookings = bs.getBookingsForUser(userId);

	    return ResponseEntity.ok(bookings);
	}
	
	
	@GetMapping("/owner/bookings")
	public ResponseEntity<List<BookingResponseDto>> getOwnerBookings(HttpSession session){

	    Integer ownerId = (Integer) session.getAttribute("USER_ID");

	    if(ownerId == null){
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    return ResponseEntity.ok(
	            bs.getBookingsForOwner(ownerId)
	    );
	}
	
	
	@PutMapping("/update/{id}/status")
	public ResponseEntity<BookingResponseDto> updateStatus(
	        @PathVariable Integer id,
	        @RequestParam BookingStatus status,
	        HttpSession session) {

	    Integer ownerId = (Integer) session.getAttribute("USER_ID");

	    if (ownerId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    BookingResponseDto updated =
	            bs.updateBookingStatus(id, status, ownerId);

	    return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/delete/booking/{id}")
	public ResponseEntity<String> deleteBooking(
	        @PathVariable Integer id,
	        HttpSession session) {

	    Integer ownerId = (Integer) session.getAttribute("USER_ID");

	    if (ownerId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required");
	    }

	    bs.deleteBooking(id, ownerId);

	    return ResponseEntity.ok("Booking deleted successfully");
	}



	
}
