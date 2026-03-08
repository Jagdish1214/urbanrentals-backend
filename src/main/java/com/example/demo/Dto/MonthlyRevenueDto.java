package com.example.demo.Dto;

public class MonthlyRevenueDto {

    private String month;
    private Double revenue;

    public MonthlyRevenueDto(String month, Double revenue) {
        this.month = month;
        this.revenue = revenue;
    }

    public String getMonth() {
        return month;
    }

    public Double getRevenue() {
        return revenue;
    }
}