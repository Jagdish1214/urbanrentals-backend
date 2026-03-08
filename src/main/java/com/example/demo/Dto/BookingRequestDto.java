package com.example.demo.Dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public class BookingRequestDto {

    @NotNull
    private Integer carId;


    @NotNull
    private LocalDate pickupDate;

    @NotNull
    private LocalDate returnDate;

    
    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
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
}
