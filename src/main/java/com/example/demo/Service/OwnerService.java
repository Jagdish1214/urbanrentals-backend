package com.example.demo.Service;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.MonthlyRevenueDto;
import com.example.demo.Dto.OwnerDashboardDto;
import com.example.demo.Entity.BookingStatus;
import com.example.demo.Entity.Bookings;
import com.example.demo.Entity.Payment;
import com.example.demo.Entity.PaymentStatus;
import com.example.demo.Repository.BookingRepo;
import com.example.demo.Repository.PaymentRepository;
import com.example.demo.Repository.carRepo;
import com.example.demo.Repository.userRepo;

@Service
public class OwnerService {

    @Autowired
    carRepo carRepo;

    @Autowired
    BookingRepo bookingRepo;

    @Autowired
    userRepo userRepo;
    
    @Autowired
    private PaymentRepository paymentRepository;

    public OwnerDashboardDto getOwnerDashboard(Integer ownerId) {

        if (ownerId == null) {
            throw new RuntimeException("Owner not logged in");
        }

        OwnerDashboardDto dto = new OwnerDashboardDto();

        // Total Cars
        dto.setTotalCars(carRepo.countByOwner_Id(ownerId));

        // Total Bookings
        dto.setTotalBookings(bookingRepo.countByCarOwnerId(ownerId));

        // Pending
        dto.setPendingBookings(
                bookingRepo.countByCarOwnerIdAndStatus(ownerId, BookingStatus.PENDING));

        // Confirmed
        dto.setConfirmedBookings(
                bookingRepo.countByCarOwnerIdAndStatus(ownerId, BookingStatus.CONFIRMED));

        // Earnings (only confirmed)
        double earnings = paymentRepository
                .findByBooking_Car_Owner_IdAndStatus(ownerId, PaymentStatus.SUCCESS)
                .stream()
                .mapToDouble(Payment::getAmount)
                .sum();

        dto.setTotalEarnings(earnings);
        
     // 📊 Monthly Revenue
        Map<Month, Double> monthlyMap = paymentRepository
                .findByBooking_Car_Owner_IdAndStatus(ownerId, PaymentStatus.SUCCESS).stream()
                .collect(Collectors.groupingBy(
                        p -> p.getPaymentDate().getMonth(),
                        Collectors.summingDouble(Payment::getAmount)
                ));

        List<MonthlyRevenueDto> monthlyRevenue = monthlyMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new MonthlyRevenueDto(
                        entry.getKey().toString().substring(0,3),
                        entry.getValue()
                ))
                .toList();

        dto.setMonthlyRevenue(monthlyRevenue);


        return dto;
    }

}
