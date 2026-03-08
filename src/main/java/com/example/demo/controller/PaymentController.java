package com.example.demo.controller;
import com.example.demo.Entity.Payment;
import com.example.demo.Entity.PaymentStatus;
import com.example.demo.Service.PaymentService;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin
public class PaymentController {
 
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // 🔹 Create Razorpay Order
    @PostMapping("/create-order/{bookingId}")
    public String createOrder(@PathVariable Integer bookingId) throws Exception {
        JSONObject order = paymentService.createOrder(bookingId);
        return order.toString();
    }

    // 🔹 Verify Payment
    @PostMapping("/verify")
    public String verifyPayment(@RequestParam String razorpayOrderId,
                                @RequestParam String razorpayPaymentId,
                                @RequestParam String razorpaySignature) {

        return paymentService.verifyPayment(
                razorpayOrderId,
                razorpayPaymentId,
                razorpaySignature
        );
    }

    // 🔹 Cash on Delivery
    @PostMapping("/cash/{bookingId}")
    public String cashOnDelivery(@PathVariable Integer bookingId) {
        return paymentService.cashOnDelivery(bookingId);
    }
    
    
    @GetMapping("/status/{bookingId}")
    public ResponseEntity<PaymentStatus> getPaymentStatus(@PathVariable Integer bookingId) {

        Payment payment = paymentService.getPaymentByBookingId(bookingId);

        return ResponseEntity.ok(payment.getStatus());
    }

    // 🔹 Get Full Payment Details (Optional)
    @GetMapping("/details/{bookingId}")
    public ResponseEntity<Payment> getPaymentDetails(@PathVariable Integer bookingId) {

        return ResponseEntity.ok(
                paymentService.getPaymentByBookingId(bookingId)
        );
    }
    
    
}