package com.example.demo.Service;

import com.example.demo.Dto.BookingResponseDto;
import com.example.demo.Dto.CarResponseDto;
import com.example.demo.Dto.RegResponseDto;
import com.example.demo.Dto.RegisterRequestDto;
import com.example.demo.Entity.*;
import com.example.demo.Repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private userRepo userRepository;

    @Autowired
    private carRepo carRepository;

    @Autowired
    private BookingRepo bookingRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    
    
    
    public Map<String, Long> getDashboardCounts() {

        long totalUsers = userRepository.count();
        long totalCars = carRepository.count();
        long totalBookings = bookingRepository.count();

        Map<String, Long> dashboardData = new HashMap<>();
        dashboardData.put("users", totalUsers);
        dashboardData.put("cars", totalCars);
        dashboardData.put("bookings", totalBookings);

        return dashboardData;
    }
    

    // ================= USERS =================

    public List<RegResponseDto> getAllUsers() {
    	

        return userRepository.findByRole(Role.USER)
                .stream()
                .map(this::convertToUserDto)
                .collect(Collectors.toList());
    }

    public RegResponseDto updateUser(RegisterRequestDto dto) {

        Users user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());

        Users saved = userRepository.save(user);

        return convertToUserDto(saved);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    // ================= CARS =================

    public List<CarResponseDto> getAllCars() {
    	
        return carRepository.findAll()
                .stream()
                .map(this::convertToCarDto)
                .collect(Collectors.toList());
    }


    public void deleteCar(Integer id) {
        carRepository.deleteById(id);
    }

    // ================= BOOKINGS =================

    public List<BookingResponseDto> getAllBookings() {

        return bookingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }



    public void deleteBooking(Integer id) {
        bookingRepository.deleteById(id);
    }

    // ================= PAYMENTS =================

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public void deletePayment(Integer id) {
        paymentRepository.deleteById(id);
    }
    

    public List<Map<String, Object>> getMonthlyCommission() {

        List<Object[]> results = paymentRepository.getMonthlyCommission();

        List<Map<String, Object>> data = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("month", row[0]);
            map.put("amount", row[1]);
            data.add(map);
        }

        return data;
    }
    
    // ================= CONVERTERS =================

    private RegResponseDto convertToUserDto(Users user) {
        return new RegResponseDto(
                "",
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    private CarResponseDto convertToCarDto(Cars car) {
        return new CarResponseDto(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getDailyPrice(),
                car.getCategory(),
                car.getTransmission(),
                car.getFuelType(),
                car.getSeatingCapacity(),
                car.getLocation(),
                car.getFeatures(),
                car.getDescription(),
                car.getImageUrl(),
                car.getOwner().getId(),
                car.getOwner().getName(),
                car.getIsAvailable()
        );
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
	    dto.setOwnerId(booking.getOwner().getName());
	   dto.setPaymentStatus(booking.getPayment().getStatus());;

	    return dto;
	}


   
}
