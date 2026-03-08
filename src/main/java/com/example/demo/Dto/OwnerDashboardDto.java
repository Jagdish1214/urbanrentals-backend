package com.example.demo.Dto;

import java.util.List;

public class OwnerDashboardDto {

    private Integer totalCars;
    private Integer totalBookings;
    private Integer pendingBookings;
    private Integer confirmedBookings;
    private Double totalEarnings;
    
    private List<MonthlyRevenueDto> monthlyRevenue;
    
	public Integer getTotalCars() {
		return totalCars;
	}
	public void setTotalCars(Integer totalCars) {
		this.totalCars = totalCars;
	}
	public Integer getTotalBookings() {
		return totalBookings;
	}
	public void setTotalBookings(Integer totalBookings) {
		this.totalBookings = totalBookings;
	}
	public Integer getPendingBookings() {
		return pendingBookings;
	}
	public void setPendingBookings(Integer pendingBookings) {
		this.pendingBookings = pendingBookings;
	}
	public Integer getConfirmedBookings() {
		return confirmedBookings;
	}
	public void setConfirmedBookings(Integer confirmedBookings) {
		this.confirmedBookings = confirmedBookings;
	}
	public Double getTotalEarnings() {
		return totalEarnings;
	}
	public void setTotalEarnings(Double totalEarnings) {
		this.totalEarnings = totalEarnings;
	}
	public List<MonthlyRevenueDto> getMonthlyRevenue() {
		return monthlyRevenue;
	}
	public void setMonthlyRevenue(List<MonthlyRevenueDto> monthlyRevenue) {
		this.monthlyRevenue = monthlyRevenue;
	}
	
	
    
}
