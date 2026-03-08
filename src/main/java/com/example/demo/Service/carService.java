package com.example.demo.Service;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Dto.CarRequestDto;
import com.example.demo.Dto.CarResponseDto;
import com.example.demo.Entity.Bookings;
import com.example.demo.Entity.Cars;
import com.example.demo.Entity.Users;
import com.example.demo.Repository.carRepo;
import com.example.demo.Repository.userRepo;



@Service
public class carService {
	
	@Autowired
	carRepo cr;
	
	@Autowired
	userRepo ur;
	
	
	public String addCar(CarRequestDto dto, MultipartFile image, Integer ownerId){

	    try {

	        // 1️⃣ Get Owner
	        Users owner = ur.findById(ownerId)
	                .orElseThrow(() -> new RuntimeException("Owner not found"));

	        // 2️⃣ Create Car Entity
	        Cars car = new Cars();
	        car.setBrand(dto.getBrand());
	        car.setModel(dto.getModel());
	        car.setYear(dto.getYear());
	        car.setDailyPrice(dto.getDailyPrice());
	        car.setCategory(dto.getCategory());
	        car.setTransmission(dto.getTransmission());
	        car.setFuelType(dto.getFuelType());
	        car.setSeatingCapacity(dto.getSeatingCapacity());
	        car.setLocation(dto.getLocation());
	        car.setFeatures(dto.getFeatures());
	        car.setDescription(dto.getDescription());

	        car.setOwner(owner);

	        
	        if (image != null && !image.isEmpty()) {

	            String fileName = System.currentTimeMillis() +
	            		"_" + image.getOriginalFilename();

	            Path uploadPath = Paths.get("uploads");

	            if (!Files.exists(uploadPath)) {
	                Files.createDirectories(uploadPath);
	            }

	            Path filePath = uploadPath.resolve(fileName);
	            Files.write(filePath, image.getBytes());

	            car.setImageUrl(fileName);
	        }

	        cr.save(car);

	        return "Car Added Successfully";

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("Failed to add car");
	    }
	}

	
    public CarResponseDto getCarById(Integer id) {

        Cars car = cr.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        return mapToResponse(car);
    }

    public List<CarResponseDto> getAllCars() {
        return cr.findAvailableCars()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<CarResponseDto> getCarsAddedByUser(Integer userId) {

        return cr.findByOwner_Id(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public List<CarResponseDto> searchCars(String keyword) {

        List<Cars> cars = cr.searchByKeyword(keyword);

        return cars.stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    public List<Cars> SearchCars(String location,
            String pickupDate,
            String returnDate) {

		LocalDate pickup = LocalDate.parse(pickupDate);
		LocalDate drop = LocalDate.parse(returnDate);
		
		if (pickup.isAfter(drop)) {
		throw new RuntimeException("Return date must be after pickup date");
		}
		
		// Basic search (location + available cars)
		return cr.findavailablecars(location);
		}
    
    

    private CarResponseDto mapToResponse(Cars car) {

        CarResponseDto dto = new CarResponseDto();
        dto.setId(car.getId());
        dto.setBrand(car.getBrand());
        dto.setModel(car.getModel());
        dto.setYear(car.getYear());
        dto.setDailyPrice(car.getDailyPrice());
        dto.setCategory(car.getCategory());
        dto.setTransmission(car.getTransmission());
        dto.setFuelType(car.getFuelType());
        dto.setSeatingCapacity(car.getSeatingCapacity());
        dto.setLocation(car.getLocation());
        dto.setFeatures(car.getFeatures());
        dto.setDescription(car.getDescription());
        dto.setImageUrl(car.getImageUrl());
        dto.setOwnerId(car.getOwner().getId());
        dto.setOwnerName(car.getOwner().getName());
        dto.setIsAvailable(car.getIsAvailable());

        return dto;
    }
    
    
    public String updateCar(Integer carId,
            CarRequestDto dto,
            MultipartFile image,
            Integer ownerId) {

				Cars car = cr.findById(carId)
				.orElseThrow(() -> new RuntimeException("Car not found"));
				
				// 🔐 Only owner can update
				if (!car.getOwner().getId().equals(ownerId)) {
				throw new RuntimeException("You are not authorized to update this car");
				}
				
				car.setBrand(dto.getBrand());
				car.setModel(dto.getModel());
				car.setYear(dto.getYear());
				car.setDailyPrice(dto.getDailyPrice());
				car.setCategory(dto.getCategory());
				car.setTransmission(dto.getTransmission());
				car.setFuelType(dto.getFuelType());
				car.setSeatingCapacity(dto.getSeatingCapacity());
				car.setLocation(dto.getLocation());
				car.setFeatures(dto.getFeatures());
				car.setDescription(dto.getDescription());
				
				try {
				if (image != null && !image.isEmpty()) {
				
				String fileName = System.currentTimeMillis() +
				        "_" + image.getOriginalFilename();
				
				Path uploadPath = Paths.get("uploads");
				
				if (!Files.exists(uploadPath)) {
				    Files.createDirectories(uploadPath);
				}
				
				Path filePath = uploadPath.resolve(fileName);
				Files.write(filePath, image.getBytes());
				
				car.setImageUrl(fileName);
				}
				
				cr.save(car);
				return "Car updated successfully";
				
				} catch (Exception e) {
					throw new RuntimeException("Failed to update car");
				}			
			}

	    public String deleteCar(Integer carId, Integer ownerId) {
	
	        Cars car = cr.findById(carId)
	                .orElseThrow(() -> new RuntimeException("Car not found"));
	
	        // 🔐 Only owner can delete
	        if (!car.getOwner().getId().equals(ownerId)) {
	            throw new RuntimeException("You are not authorized to delete this car");
	        }
	
	        cr.delete(car);
	
	        return "Car deleted successfully";
	    }
	    
	    
	    public String updateCarStatus(Integer carId,
                Boolean status,
                Integer ownerId) {

				Cars car = cr.findById(carId)
				.orElseThrow(() -> new RuntimeException("Car not found"));
				
				// 🔐 Only owner can change status
				if (!car.getOwner().getId().equals(ownerId)) {
					throw new RuntimeException("You are not authorized to update status");
				}
				
				car.setIsAvailable(status);
				
				cr.save(car);
				
				return "Car availability updated successfully";
			}

    
    
    
    
	    private Cars mapToEntity(CarRequestDto dto) {

        Cars car = new Cars();
        car.setBrand(dto.getBrand());
        car.setModel(dto.getModel());
        car.setYear(dto.getYear());
        car.setDailyPrice(dto.getDailyPrice());
        car.setCategory(dto.getCategory());
        car.setTransmission(dto.getTransmission());
        car.setFuelType(dto.getFuelType());
        car.setSeatingCapacity(dto.getSeatingCapacity());
        car.setLocation(dto.getLocation());
        car.setFeatures(dto.getFeatures());
        car.setDescription(dto.getDescription());
        car.setImageUrl(dto.getImageUrl());
        car.setIsAvailable(dto.getIsAvailable());

        return car;
    }
	
}
