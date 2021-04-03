package com.zbodya.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.zbodya.model.Flight;
import com.zbodya.model.FlightRepository;
import com.zbodya.model.FlightSortAndPageRepository;
import com.zbodya.model.Tourist;

@Service
public class FlightService
{
	
	@Autowired
	FlightRepository repo;
	
	@Autowired
	FlightSortAndPageRepository sprepo;

	EntityManager entityManager = DBService.openDBConnection();					
	Query query =  entityManager.createQuery("SELECT f FROM Flight f");
	List<Flight> flights = query.getResultList();
	
	public Page<Flight> findPaginated(Pageable pageable)
	{
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Flight>list;
		
		if(flights.size()<startItem) 
		{
			list = Collections.EMPTY_LIST;
		}else 
		{
			int toIndex = Math.min(startItem + pageSize, flights.size());
			list = flights.subList(startItem,toIndex);
		}
		
		Page<Flight> flightPage = new PageImpl<Flight>(list,PageRequest.of(currentPage,pageSize),flights.size());
		
		return flightPage;
		
	}
	
	public Page<Flight> findPaginatedSortByTicketPrice(Pageable pageable)
	{
		List<Flight>flights =  (List<Flight>) repo.findAll();
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Flight>list;
		
		if(flights.size()<startItem) 
		{
			list = Collections.EMPTY_LIST;
		}else 
		{
			int toIndex = Math.min(startItem + pageSize, flights.size());
			list = flights.subList(startItem,toIndex);
		}
		
		Pageable sortedByTicketPrice = PageRequest.of(currentPage, pageSize,Sort.by("ticketPrice"));
		Page<Flight> flightPage = sprepo.findAll(sortedByTicketPrice);
		
		return flightPage;
	}
	
	public Page<Flight> findPaginatedSortByArrival(Pageable pageable)
	{
		List<Flight>flights =  (List<Flight>) repo.findAll();
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Flight>list;
		
		if(flights.size()<startItem) 
		{
			list = Collections.EMPTY_LIST;
		}else 
		{
			int toIndex = Math.min(startItem + pageSize, flights.size());
			list = flights.subList(startItem,toIndex);
		}
		
		Pageable sortedByArrival = PageRequest.of(currentPage, pageSize,Sort.by("arrivalTime"));
		Page<Flight> flightPage = sprepo.findAll(sortedByArrival);
		
		return flightPage;
	}
	
	public Page<Flight> findPaginatedSortByDeparture(Pageable pageable)
	{
		List<Flight>flights =  (List<Flight>) repo.findAll();
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Flight>list;
		
		if(flights.size()<startItem) 
		{
			list = Collections.EMPTY_LIST;
		}else 
		{
			int toIndex = Math.min(startItem + pageSize, flights.size());
			list = flights.subList(startItem,toIndex);
		}
		
		Pageable sortedByDeparture = PageRequest.of(currentPage, pageSize,Sort.by("departureTime"));
		Page<Flight> flightPage = sprepo.findAll(sortedByDeparture);
		
		return flightPage;
	}
	
	public Page<Flight> findPaginatedSearched(Pageable pageable,LocalDateTime search)
	{
		List<Flight>flights = (List<Flight>) repo.findByArrivalTime(search);

		System.out.println("Tourists : " + flights);
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Flight>list;
		
		if(flights.size()<startItem) 
		{
			list = Collections.EMPTY_LIST;
		}else 
		{
			int toIndex = Math.min(startItem + pageSize, flights.size());
			list = flights.subList(startItem,toIndex);
		}
		
		Page<Flight> touristPage = new PageImpl<Flight>(list,PageRequest.of(currentPage,pageSize),flights.size());
		
		return touristPage;
		
	}
	
	
	
}
