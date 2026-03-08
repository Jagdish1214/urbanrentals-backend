package com.example.demo.controller;

import com.example.demo.Dto.BookingResponseDto;
import com.example.demo.Dto.CarResponseDto;
import com.example.demo.Dto.RegResponseDto;
import com.example.demo.Dto.RegisterRequestDto;
import com.example.demo.Entity.*;
import com.example.demo.Repository.BookingRepo;
import com.example.demo.Repository.carRepo;
import com.example.demo.Repository.userRepo;
import com.example.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @GetMapping("/dashboard-count")
    public ResponseEntity<?> getDashboardCounts() {
        return ResponseEntity.ok(adminService.getDashboardCounts());
        
    }
    @GetMapping("/admin/monthly-commission")
    public List<Map<String, Object>> getMonthlyCommission() {
        return adminService.getMonthlyCommission();
    }

    // ================= USERS =================

    @GetMapping("/users")
    public List<RegResponseDto> getAllUsers() {
        return adminService.getAllUsers();
    }

    @PutMapping("/users")
    public RegResponseDto updateUser(@RequestBody RegisterRequestDto user) {
        return adminService.updateUser(user);
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Integer id) {
        adminService.deleteUser(id);
        return "User deleted";
    }

    // ================= CARS =================

    @GetMapping("/cars")
    public List<CarResponseDto> getAllCars() {
        return adminService.getAllCars();
    }

    @DeleteMapping("/cars/{id}")
    public String deleteCar(@PathVariable Integer id) {
        adminService.deleteCar(id);
        return "Car deleted";
    }

    // ================= BOOKINGS =================

    @GetMapping("/bookings")
    public List<BookingResponseDto> getAllBookings() {
        return adminService.getAllBookings();
    }

    
    @DeleteMapping("/delete/bookings/{id}")
    public String deleteBooking(@PathVariable Integer id) {
        adminService.deleteBooking(id);
        return "Booking deleted";
    }

    // ================= PAYMENTS =================

    @GetMapping("/payments")
    public List<Payment> getAllPayments() {
        return adminService.getAllPayments();
    }

    

    @DeleteMapping("/payments/{id}")
    public String deletePayment(@PathVariable Integer id) {
        adminService.deletePayment(id);
        return "Payment deleted";
    }
}
