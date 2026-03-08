package com.example.demo.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String razorpayOrderId;
    private String razorpayPaymentId;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    private Double amount;

    private LocalDateTime paymentDate;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Bookings booking;
    
    private Double adminCommission;   // 10%
    private Double ownerAmount; 
    

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getRazorpayOrderId() {
		return razorpayOrderId;
	}



	public void setRazorpayOrderId(String razorpayOrderId) {
		this.razorpayOrderId = razorpayOrderId;
	}



	public String getRazorpayPaymentId() {
		return razorpayPaymentId;
	}



	public void setRazorpayPaymentId(String razorpayPaymentId) {
		this.razorpayPaymentId = razorpayPaymentId;
	}



	public PaymentStatus getStatus() {
		return status;
	}



	public void setStatus(PaymentStatus status) {
		this.status = status;
	}



	public PaymentMethod getMethod() {
		return method;
	}



	public void setMethod(PaymentMethod method) {
		this.method = method;
	}



	public Double getAmount() {
		return amount;
	}



	public void setAmount(Double amount) {
		this.amount = amount;
	}



	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}



	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}



	public Bookings getBooking() {
		return booking;
	}



	public void setBooking(Bookings booking) {
		this.booking = booking;
	}
	
	

	


	public Double getAdminCommission() {
		return adminCommission;
	}



	public void setAdminCommission(Double adminCommission) {
		this.adminCommission = adminCommission;
	}



	public Double getOwnerAmount() {
		return ownerAmount;
	}



	public void setOwnerAmount(Double ownerAmount) {
		this.ownerAmount = ownerAmount;
	}



	


	public Payment(Integer id, String razorpayOrderId, String razorpayPaymentId, PaymentStatus status,
			PaymentMethod method, Double amount, LocalDateTime paymentDate, Bookings booking, Double adminCommission,
			Double ownerAmount) {
		super();
		this.id = id;
		this.razorpayOrderId = razorpayOrderId;
		this.razorpayPaymentId = razorpayPaymentId;
		this.status = status;
		this.method = method;
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.booking = booking;
		this.adminCommission = adminCommission;
		this.ownerAmount = ownerAmount;
	}



	public Payment() {
	}
    
    
    
    
}
