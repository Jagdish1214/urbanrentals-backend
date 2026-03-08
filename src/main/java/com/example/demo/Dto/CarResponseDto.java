package com.example.demo.Dto;

import com.example.demo.Entity.Users;

public class CarResponseDto {
	private Integer id;
    private String brand;
    private String model;
    private Integer year;
    private Double dailyPrice;
    private String category;
    private String transmission;
    private String fuelType;
    private Integer seatingCapacity;
    private String location;
    private String features;
    private String description;

    private String imageUrl;

    private Integer ownerId;
    private String ownerName;
    private Boolean isAvailable = true;

	
    
    
    
    public CarResponseDto() {
	}





	public CarResponseDto(Integer id, String brand, String model, Integer year, Double dailyPrice, String category,
			String transmission, String fuelType, Integer seatingCapacity, String location, String features,
			String description, String imageUrl, Integer ownerId, String ownerName, Boolean isAvailable) {
		super();
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.dailyPrice = dailyPrice;
		this.category = category;
		this.transmission = transmission;
		this.fuelType = fuelType;
		this.seatingCapacity = seatingCapacity;
		this.location = location;
		this.features = features;
		this.description = description;
		this.imageUrl = imageUrl;
		this.ownerId = ownerId;
		this.ownerName = ownerName;
		this.isAvailable = isAvailable;
	}





	public Integer getId() {
		return id;
	}





	public void setId(Integer id) {
		this.id = id;
	}





	public String getBrand() {
		return brand;
	}





	public void setBrand(String brand) {
		this.brand = brand;
	}





	public String getModel() {
		return model;
	}





	public void setModel(String model) {
		this.model = model;
	}





	public Integer getYear() {
		return year;
	}





	public void setYear(Integer year) {
		this.year = year;
	}





	public Double getDailyPrice() {
		return dailyPrice;
	}





	public void setDailyPrice(Double dailyPrice) {
		this.dailyPrice = dailyPrice;
	}





	public String getCategory() {
		return category;
	}





	public void setCategory(String category) {
		this.category = category;
	}





	public String getTransmission() {
		return transmission;
	}





	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}





	public String getFuelType() {
		return fuelType;
	}





	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}





	public Integer getSeatingCapacity() {
		return seatingCapacity;
	}





	public void setSeatingCapacity(Integer seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}





	public String getLocation() {
		return location;
	}





	public void setLocation(String location) {
		this.location = location;
	}





	public String getFeatures() {
		return features;
	}





	public void setFeatures(String features) {
		this.features = features;
	}





	public String getDescription() {
		return description;
	}





	public void setDescription(String description) {
		this.description = description;
	}





	public String getImageUrl() {
		return imageUrl;
	}





	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}





	public Integer getOwnerId() {
		return ownerId;
	}





	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}





	public String getOwnerName() {
		return ownerName;
	}





	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}





	public Boolean getIsAvailable() {
		return isAvailable;
	}





	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}


	

    
}
