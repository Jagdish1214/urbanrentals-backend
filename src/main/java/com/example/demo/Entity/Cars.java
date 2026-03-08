package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cars")
public class Cars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String brand;

    @NotNull
    private String model;

    @NotNull
    private Integer year;

    @NotNull
    private Double dailyPrice;

    @NotNull
    private String category; 
    

    @NotNull
    private String transmission;
    

    @NotNull
    private String fuelType;
   

    @NotNull
    private Integer seatingCapacity;

    @NotNull
    private String location;

    @Size(max=500)
    private String features;
    

    @Size(max=500)
    private String description;
    
    @NotNull
    private String imageUrl;
    
    @NotNull
    @Column(nullable = false)
    private Boolean isAvailable = true;


    
    
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Users owner;


    public Cars() {
    }


	public Cars(Integer id, @NotNull String brand, @NotNull String model, @NotNull Integer year,
			@NotNull Double dailyPrice, @NotNull String category, @NotNull String transmission,
			@NotNull String fuelType, @NotNull Integer seatingCapacity, @NotNull String location,
			@Size(max = 500) String features, @Size(max = 500) String description, @NotNull String imageUrl,
			@NotNull Boolean isAvailable, Users owner) {
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
		this.isAvailable = isAvailable;
		this.owner = owner;
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


	public Users getOwner() {
		return owner;
	}


	public void setOwner(Users owner) {
		this.owner = owner;
	}
	
	public String getImageUrl() {
	    return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
	    this.imageUrl = imageUrl;
	}
	
	public Boolean getIsAvailable() {
	    return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
	    this.isAvailable = isAvailable;
	}



   
}
