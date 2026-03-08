package com.example.demo.Service;

import com.example.demo.Entity.*;
import com.example.demo.Entity.Payment;
import com.example.demo.Repository.*;
import com.razorpay.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

	@Value("${razorpay.key.id}")
	private String keyId;

	@Value("${razorpay.key.secret}")
	private String keySecret;

    private final PaymentRepository paymentRepo;
    private final BookingRepo bookingRepo;
    

    public PaymentService(PaymentRepository paymentRepo, BookingRepo bookingRepo) {
        this.paymentRepo = paymentRepo;
        this.bookingRepo = bookingRepo;
		
    }
    
    public Payment getPaymentByBookingId(Integer bookingId) {

        return paymentRepo.findTopByBookingIdOrderByPaymentDateDesc(bookingId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    // 🔹 Create Razorpay Order (For UPI / Online)
    public JSONObject createOrder(Integer bookingId) throws Exception {

        Bookings booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject options = new JSONObject();
        options.put("amount", booking.getPrice() * 100); // paise
        options.put("currency", "INR");
        options.put("receipt", "txn_" + bookingId);

        Order order = client.orders.create(options);

        // Save Payment
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(booking.getPrice());
        payment.setMethod(PaymentMethod.UPI);
        payment.setRazorpayOrderId(order.get("id"));
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDateTime.now());

        paymentRepo.save(payment);

        return order.toJson();
    }

 // 🔹 Verify Payment
    public String verifyPayment(String razorpayOrderId,
                                String razorpayPaymentId,
                                String razorpaySignature) {

        Payment payment = paymentRepo
                .findByRazorpayOrderId(razorpayOrderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        double totalAmount = payment.getAmount();

        // 🔥 10% commission
        double commission = totalAmount * 0.10;
        double ownerAmount = totalAmount - commission;

        payment.setRazorpayPaymentId(razorpayPaymentId);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setAdminCommission(commission);
        payment.setOwnerAmount(ownerAmount);
        payment.setPaymentDate(LocalDateTime.now());

        // Confirm booking
        Bookings booking = payment.getBooking();
        booking.setStatus(BookingStatus.CONFIRMED);

        paymentRepo.save(payment);
        bookingRepo.save(booking);

        return "Payment Successful";
    }


    // 🔹 Cash on Delivery
    public String cashOnDelivery(Integer bookingId) {

        Bookings booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(booking.getPrice());
        payment.setMethod(PaymentMethod.CASH);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDateTime.now());

        booking.setStatus(BookingStatus.CONFIRMED);

        paymentRepo.save(payment);

        return "Cash on Delivery Selected";
    }
}

