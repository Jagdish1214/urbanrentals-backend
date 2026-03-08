package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Cars;

@Repository
public interface carRepo extends JpaRepository<Cars,Integer> {
	@Query("""
	        SELECT c FROM Cars c
	        WHERE
	        LOWER(c.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))
	        OR LOWER(c.model) LIKE LOWER(CONCAT('%', :keyword, '%'))
	        OR LOWER(c.category) LIKE LOWER(CONCAT('%', :keyword, '%'))
	        OR LOWER(c.fuelType) LIKE LOWER(CONCAT('%', :keyword, '%'))
	        OR LOWER(c.transmission) LIKE LOWER(CONCAT('%', :keyword, '%'))
	        OR LOWER(c.location) LIKE LOWER(CONCAT('%', :keyword, '%'))
	        OR LOWER(c.features) LIKE LOWER(CONCAT('%', :keyword, '%'))
	        OR CAST(c.year AS string) LIKE CONCAT('%', :keyword, '%')
	        OR CAST(c.dailyPrice AS string) LIKE CONCAT('%', :keyword, '%')
	    """)
	    List<Cars> searchByKeyword(@Param("keyword") String keyword);
	
	
	@Query("""
		    SELECT c FROM Cars c
		    WHERE c.isAvailable = true
		      AND c.id NOT IN (
		          SELECT b.car.id FROM Bookings b
		          WHERE b.status IN ('PENDING', 'CONFIRMED')
		      )
		""")
		List<Cars> findAvailableCars();
	
	
	@Query("SELECT c FROM Cars c WHERE c.location = :location AND c.isAvailable = true")
	List<Cars> findavailablecars(@Param("location") String location);

	
	Integer countByOwner_Id(Integer ownerId);
	
	List<Cars> findByOwner_Id(Integer ownerId);




	
}
