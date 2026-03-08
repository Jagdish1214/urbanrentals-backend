package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.Bookings;
import com.example.demo.Entity.Payment;
import com.example.demo.Entity.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	void deleteById(Integer id);
	
	List<Payment> findByBooking_Car_Owner_IdAndStatus(
            Integer ownerId,
            PaymentStatus status
    );

	Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);
	
	@Query("""
			SELECT 
			    MONTH(p.paymentDate) as month,
			    SUM(p.adminCommission) as totalCommission
			FROM Payment p
			WHERE p.status = com.example.demo.Entity.PaymentStatus.SUCCESS
			GROUP BY MONTH(p.paymentDate)
			ORDER BY MONTH(p.paymentDate)
			""")
			List<Object[]> getMonthlyCommission();

	

	Optional<Payment> findTopByBookingIdOrderByPaymentDateDesc(Integer bookingId);
	
}
