package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.BookingStatus;
import com.example.demo.Entity.Bookings;
import com.example.demo.Entity.Cars;

public interface BookingRepo extends JpaRepository<Bookings,Long>{
	
	List<Bookings> findByUserId(Integer userId);
	
	
	
	Integer countByOwner_Id(Integer userId);

	Integer countByCarOwnerIdAndStatus(Integer ownerId, BookingStatus status);
	
	Integer countByCarOwnerId(Integer ownerId);

		
	List<Bookings> findByUser_Id(Integer userId);
	
	List<Bookings> findByCar_Owner_Id(Integer ownerId);



	Optional<Bookings> findById(Integer bookingId);



	void deleteById(Integer id);




}
