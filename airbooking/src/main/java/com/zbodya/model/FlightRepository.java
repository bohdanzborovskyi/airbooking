package com.zbodya.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FlightRepository extends CrudRepository<Flight,Long>
{
	@Query(value = "select * from Flight f where f.arrival_time > :keyword" , nativeQuery = true)
	List<Flight> findByArrivalTime(@Param("keyword")LocalDateTime keyword);
}
