package com.zbodya.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zbodya.model.Flight;
import com.zbodya.model.Tourist;
import com.zbodya.service.DBService;
import com.zbodya.service.FlightService;

@Controller
@RequestMapping("/flights")
public class FlightController 
{	
	
	@Autowired
	FlightService fservice;
	
	
	@GetMapping()
	public String getAllFlights(Model model, @RequestParam("size")Optional<Integer>size, @RequestParam("page")Optional<Integer>page) 
	{
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		
		Page<Flight> flightPage = fservice.findPaginated(PageRequest.of(currentPage-1, pageSize));		
		model.addAttribute("flights", flightPage);
		
		if(flightPage.getTotalPages()>0) 
		{
			List<Integer> pageNumbers = IntStream.rangeClosed(1,flightPage.getTotalPages()).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers.size());				
			model.addAttribute("page",currentPage-1);
		}
		return "flights";
	}	
	
	@GetMapping("search")
	public String searchByKeyword(@DateTimeFormat(iso = ISO.DATE_TIME)@RequestParam("keyword")Optional<LocalDateTime> keyword, @RequestParam("size") Optional<Integer> size,@RequestParam("page")Optional<Integer> page, Model model) 
	{
		LocalDateTime search;
		System.out.println("Keyword: " + model.getAttribute("keyword"));
		if(keyword==null && model.getAttribute("keyword")!=null)  search = (LocalDateTime) model.getAttribute("keyword");
		else  search = keyword.orElse(LocalDateTime.now());
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		
		Page<Flight> flightPage = fservice.findPaginatedSearched(PageRequest.of(currentPage-1, pageSize),search);		
		model.addAttribute("flights", flightPage);
		
		if(flightPage.getTotalPages()>0) 
		{
			List<Integer> pageNumbers = IntStream.rangeClosed(1,flightPage.getTotalPages()).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers.size());
				
		}
		model.addAttribute("page",currentPage-1);		
		model.addAttribute("sort","/search");
		model.addAttribute("keyword", search);
		return "flights";
	}
	
	@GetMapping("sortByTicketPrice")
	public String getAllFlightsByTicketPrice(Model model, @RequestParam("size") Optional<Integer> size,@RequestParam("page")Optional<Integer> page) 
	{
	
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		
		Page<Flight> flightPage = fservice.findPaginatedSortByTicketPrice(PageRequest.of(currentPage-1, pageSize));		
		model.addAttribute("flights", flightPage);
		
		if(flightPage.getTotalPages()>0) 
		{
			List<Integer> pageNumbers = IntStream.rangeClosed(1,flightPage.getTotalPages()).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers.size());
		}
		model.addAttribute("sort","/sortByTicketPrice");
		model.addAttribute("page",currentPage-1);
		return "flights";
	}
	
	@GetMapping("sortByArrival")
	public String getAllFlightsByArrival(Model model, @RequestParam("size") Optional<Integer> size,@RequestParam("page")Optional<Integer> page) 
	{
	
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		
		Page<Flight> flightPage = fservice.findPaginatedSortByArrival(PageRequest.of(currentPage-1, pageSize));		
		model.addAttribute("flights", flightPage);
		
		if(flightPage.getTotalPages()>0) 
		{
			List<Integer> pageNumbers = IntStream.rangeClosed(1,flightPage.getTotalPages()).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers.size());
		}
		model.addAttribute("sort","/sortByArrival");
		model.addAttribute("page",currentPage-1);
		return "flights";
	}
	
	@GetMapping("sortByDeparture")
	public String getAllFlightsByDeparture(Model model, @RequestParam("size") Optional<Integer> size,@RequestParam("page")Optional<Integer> page) 
	{
	
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		
		Page<Flight> flightPage = fservice.findPaginatedSortByDeparture(PageRequest.of(currentPage-1, pageSize));		
		model.addAttribute("flights", flightPage);
		
		if(flightPage.getTotalPages()>0) 
		{
			List<Integer> pageNumbers = IntStream.rangeClosed(1,flightPage.getTotalPages()).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers.size());
		}
		model.addAttribute("sort","/sortByDeparture");
		model.addAttribute("page",currentPage-1);
		return "flights";
	}
	
	@GetMapping("delete/{id}")
	public String deleteFlight(@PathVariable("id")long id, Model model) 
	{		
		EntityManager manager = DBService.openDBConnection();			
		Query findFlight = manager.createQuery("SELECT f FROM Flight f where f.id=" + id);
		Flight flight = (Flight) findFlight.getSingleResult();
		manager.remove(flight);
		DBService.closeDBConnection(manager);
		return "redirect:/flights";
	}
	
	@GetMapping("addFlightForm")
	public String addFlightForm(Flight flight)
	{
		return "addFlightForm";
	}
	
	@PostMapping("addFlight")
	public String addFlight(@Valid Flight flight,BindingResult bindingResult, Model model) 
	{
		if(bindingResult.hasErrors())return "addFlightForm";
		else 
		{
		EntityManager manager = DBService.openDBConnection();		
		manager.persist(flight);			
		DBService.closeDBConnection(manager);
		return "redirect:/flights";
		}
	}
	
	@GetMapping("editFlight/{id}")
	public String editFlight(@PathVariable long id, Model model) 
	{
		EntityManager entityManager = DBService.openDBConnection();							
		Query query =  entityManager.createQuery("SELECT f FROM Flight f WHERE id =" + id );
		Flight flight =(Flight) query.getSingleResult();		
		DBService.closeDBConnection(entityManager);
		model.addAttribute("flight", flight);
		return "updateFlight";
	}
	
	@PostMapping("updateFlight/{id}")
	public String updateFlight(@PathVariable long id, @Valid Flight flight,BindingResult bindingResult, Model model) 
	{
		if(bindingResult.hasErrors())return "updateFlight";
		else 
		{
		EntityManager entityManager = DBService.openDBConnection();	
		entityManager.merge(flight);		
		DBService.closeDBConnection(entityManager);	
		return "redirect:/flights";
		}
	}
	
	
}
