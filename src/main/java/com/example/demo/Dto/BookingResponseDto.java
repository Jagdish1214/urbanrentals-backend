package com.example.demo.Dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.demo.Entity.BookingStatus;
import com.example.demo.Entity.Payment;
import com.example.demo.Entity.PaymentStatus;

public class BookingResponseDto {

    private Integer bookingId;
    private CarResponseDto car;  
    private String userName;
    private String carModel;
    private String ownerId;
    private LocalDate pickupDate;
    private LocalDate returnDate;
    private BookingStatus status;
    private Double price;
    private LocalDateTime createdAt;
    private PaymentStatus paymentStatus;
	
    
    
    
    
    
    public BookingResponseDto() {
		
	}






	public BookingResponseDto(Integer bookingId, CarResponseDto car, String userName, String carModel, String ownerId,
			LocalDate pickupDate, LocalDate returnDate, BookingStatus status, Double price, 
			LocalDateTime createdAt,PaymentStatus paymentStatus) {
		super();
		this.bookingId = bookingId;
		this.car = car;
		this.userName = userName;
		this.carModel = carModel;
		this.ownerId = ownerId;
		this.pickupDate = pickupDate;
		this.returnDate = returnDate;
		this.status = status;
		this.price = price;
		this.createdAt = createdAt;
		this.paymentStatus=paymentStatus;
	}






	public Integer getBookingId() {
		return bookingId;
	}






	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}






	public CarResponseDto getCar() {
		return car;
	}






	public void setCar(CarResponseDto car) {
		this.car = car;
	}






	public String getUserName() {
		return userName;
	}






	public void setUserName(String userName) {
		this.userName = userName;
	}






	public String getCarModel() {
		return carModel;
	}






	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}






	public String getOwnerId() {
		return ownerId;
	}






	public void setOwnerId(String string) {
		this.ownerId = string;
	}






	public LocalDate getPickupDate() {
		return pickupDate;
	}






	public void setPickupDate(LocalDate pickupDate) {
		this.pickupDate = pickupDate;
	}






	public LocalDate getReturnDate() {
		return returnDate;
	}






	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}






	public BookingStatus getStatus() {
		return status;
	}






	public void setStatus(BookingStatus status) {
		this.status = status;
	}






	public Double getPrice() {
		return price;
	}






	public void setPrice(Double price) {
		this.price = price;
	}






	public LocalDateTime getCreatedAt() {
		return createdAt;
	}






	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}






	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}






	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	
    
    
    
}
