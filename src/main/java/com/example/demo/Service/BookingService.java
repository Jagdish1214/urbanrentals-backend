package com.example.demo.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.BookingRequestDto;
import com.example.demo.Dto.BookingResponseDto;
import com.example.demo.Dto.CarResponseDto;
import com.example.demo.Entity.BookingStatus;
import com.example.demo.Entity.Bookings;
import com.example.demo.Entity.Cars;
import com.example.demo.Entity.Users;
import com.example.demo.Repository.BookingRepo;
import com.example.demo.Repository.carRepo;
import com.example.demo.Repository.userRepo;

@Service
public class BookingService {
	
	@Autowired
	BookingRepo bookingRepo;
	
	@Autowired
	carRepo cr;
	
	@Autowired
	userRepo ur;
	
	@Autowired
	EmailService emailService;
	
	
	public BookingResponseDto createBooking(BookingRequestDto dto,Integer userId) {

	    Cars car = cr.findById(dto.getCarId())
	            .orElseThrow(() -> new RuntimeException("Car not found"));
	    Users user = ur.findById(userId)
	            .orElseThrow(() -> new RuntimeException("Car not found"));

	    if (dto.getReturnDate().isBefore(dto.getPickupDate())) {
	        throw new RuntimeException("Return date must be after pickup date");
	    }
	    
	    if (user.getId().equals(car.getOwner().getId())) {
	        throw new RuntimeException("Owner cannot book their own car");
	    }

	    Bookings booking = new Bookings();
	    booking.setCar(car);
	    booking.setUser(user);
	    booking.setOwner(car.getOwner());
	    booking.setPickupDate(dto.getPickupDate());
	    booking.setReturnDate(dto.getReturnDate());
	    booking.setStatus(BookingStatus.PENDING);

	    long days = ChronoUnit.DAYS.between(
	            dto.getPickupDate(), dto.getReturnDate());

	    booking.setPrice(days * car.getDailyPrice());

	    Bookings saved = bookingRepo.save(booking);
	    
	    car.setIsAvailable(false);

        cr.save(car);
        
        sendBookingEmails(saved);

	    return mapToResponse(saved);
	    
	    
	}
	
	private void sendBookingEmails(Bookings booking) {

	    try {
	        // Customer Email
	        emailService.sendEmail(
	                booking.getUser().getEmail(),
	                "Your car has been Booked!!! " ,
	                "Dear " + booking.getUser().getName() +
	                        ",\n\nYour booking for " +
	                        booking.getCar().getBrand() + " " +
	                        booking.getCar().getModel() +
	                        "\nFrom: " + booking.getPickupDate() +
	                        "\nTo: " + booking.getReturnDate() +
	                        "\nTotal Price: ₹" + booking.getPrice() +
	                        "\nApproval from owner : "+booking.getStatus()+
	                        "\nAs soon as the status is CONFIRM you can pay and enjoy your ride"+
	                        "\nand enjoy our services!"+
	                        "\n\nThank you choosing UrbanRentals..."
	        );
	        
	        // Owner Email
	        emailService.sendEmail(
	                booking.getOwner().getEmail(),
	                "New Booking Received",
	                "Dear " + booking.getOwner().getName() +
	                        ",\n\nYour car " +
	                        booking.getCar().getBrand() + " " +
	                        booking.getCar().getModel() +
	                        " has been booked." +
	                        "\nCustomer: " + booking.getUser().getName() +
	                        "\nFrom: " + booking.getPickupDate() +
	                        "\nTo: " + booking.getReturnDate() +
	                        "\n\nPlease check dashboard for details."
	        );

	    } catch (Exception e) {
	        System.out.println("Email sending failed: " + e.getMessage());
	    }
	}

	
	
	
	public List<BookingResponseDto> getBookingsForUser(Integer userId){
		
		return bookingRepo.findByUserId(userId)
	            .stream()
	            .map(this::mapToResponse)
	            .collect(Collectors.toList());
	}
	
	public List<BookingResponseDto> getBookingsForOwner(Integer ownerId){

	    return bookingRepo.findByCar_Owner_Id(ownerId)
	            .stream()
	            .map(this::mapToResponse)
	            .collect(Collectors.toList());
	}
	
	
	public BookingResponseDto updateBookingStatus(
	        Integer bookingId,
	        BookingStatus status,
	        Integer ownerId) {

	    Bookings booking = bookingRepo.findById(bookingId)
	            .orElseThrow(() -> new RuntimeException("Booking not found"));

	    // 🔐 Make sure logged-in owner owns this car
	    if (!booking.getCar().getOwner().getId().equals(ownerId)) {
	        throw new RuntimeException("You are not authorized to update this booking");
	    }

	    booking.setStatus(status);

	    // If cancelled → make car available again
	    if (status == BookingStatus.CANCELLED) {
	        booking.getCar().setIsAvailable(true);
	        cr.save(booking.getCar());
	    }

	    Bookings updated = bookingRepo.save(booking);

	    return mapToResponse(updated);
	}

	public void deleteBooking(Integer bookingId, Integer ownerId) {

	    Bookings booking = bookingRepo.findById(bookingId)
	            .orElseThrow(() -> new RuntimeException("Booking not found"));

	    // 🔐 Only owner of the car can delete
	    if (!booking.getCar().getOwner().getId().equals(ownerId)) {
	        throw new RuntimeException("You are not authorized to delete this booking");
	    }

	    // Make car available again
	    booking.getCar().setIsAvailable(true);
	    cr.save(booking.getCar());

	    bookingRepo.delete(booking);
	}



	
	
	
	
	private BookingResponseDto mapToResponse(Bookings booking) {

	    CarResponseDto carDto = new CarResponseDto();
	    carDto.setId(booking.getCar().getId());
	    carDto.setBrand(booking.getCar().getBrand());
	    carDto.setModel(booking.getCar().getModel());
	    carDto.setYear(booking.getCar().getYear());
	    carDto.setCategory(booking.getCar().getCategory());
	    carDto.setImageUrl(booking.getCar().getImageUrl());
	    carDto.setLocation(booking.getCar().getLocation());

	    BookingResponseDto dto = new BookingResponseDto();
	    dto.setBookingId(booking.getId());
	    dto.setCar(carDto);
	    dto.setUserName(booking.getUser().getName());
	    dto.setCarModel(booking.getCar().getModel());
	    dto.setPickupDate(booking.getPickupDate());
	    dto.setReturnDate(booking.getReturnDate());
	    dto.setStatus(booking.getStatus());
	    dto.setPrice(booking.getPrice());
	    dto.setCreatedAt(booking.getCreatedAt());

	    return dto;
	}


	
	
	
}
