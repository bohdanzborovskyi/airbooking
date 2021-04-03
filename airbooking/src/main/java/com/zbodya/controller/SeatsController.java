package com.zbodya.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zbodya.model.Flight;
import com.zbodya.model.FlightRepository;
import com.zbodya.model.Tourist;
import com.zbodya.model.TouristFlight;
import com.zbodya.model.TouristRepository;
import com.zbodya.service.DBService;

@Controller
@RequestMapping("/seats")
public class SeatsController
{
	
	@Autowired 
	TouristRepository trepo;
	
	@Autowired
	FlightRepository frepo;
	
	private long cacheID;
	private String previous;
	
	@GetMapping("seatsByFlight/{id}")
	public String getAllSeatsByFlight(@PathVariable("id")long id,Model model) 
	{		
		previous = "flight";
		cacheID = id;
		EntityManager manager = DBService.openDBConnection();	
		Query findSeats = manager.createQuery("SELECT tf FROM TouristFlight tf WHERE flight=" +  id );
		List<TouristFlight> seats = (List<TouristFlight>) findSeats.getResultList();	
		DBService.closeDBConnection(manager);
		model.addAttribute("seats", seats);
		return "seats";
	}
	
	@GetMapping("seatsByTourist/{id}")
	public String getAllSeatsByTourist(@PathVariable("id")long id,Model model) 
	{		
		previous = "tourist";
		cacheID = id;
		EntityManager manager = DBService.openDBConnection();	
		Query findSeats = manager.createQuery("SELECT tf FROM TouristFlight tf WHERE tourist=" +  id );
		List<TouristFlight> seats = (List<TouristFlight>) findSeats.getResultList();	
		DBService.closeDBConnection(manager);
		model.addAttribute("seats", seats);
		return "seats";
	}
	
	@GetMapping("delete/{id}")
	public String deleteFlight(@PathVariable("id")long id, Model model) 
	{				
		EntityManager manager = DBService.openDBConnection();		
		Query findSeat = manager.createQuery("SELECT tf FROM TouristFlight tf where id=" + id);
		TouristFlight seat = (TouristFlight) findSeat.getSingleResult();
		manager.remove(seat);	
		Query query;
		if(previous.equals("flight"))
		{
			 query =  manager.createQuery("SELECT tf FROM TouristFlight tf where flight=" + cacheID);
		}else 
		{
			 query =  manager.createQuery("SELECT tf FROM TouristFlight tf where tourist=" + cacheID);
		}
		List<TouristFlight>seats =query.getResultList();		
		DBService.closeDBConnection(manager);
		model.addAttribute("seats", seats);
		return "seats";
	}
	
	@GetMapping("addSeatForm")
	public String addSeat(Model model)
	{
		List<Flight>flights = (List<Flight>) frepo.findAll();
		List<Tourist>tourists = (List<Tourist>) trepo.findAll();
		model.addAttribute("tourists", tourists);
		model.addAttribute("flights", flights);
		return "addTouristFlightForm";
	}
	
	@GetMapping("findSeats")
	public String add(@RequestParam("touristid")Long touristid,@RequestParam("flightid")Long flightid, Model model,HttpSession session) 
	{		
		if(flightid==null || touristid==null)return "redirect:/seats/addSeatForm";
		EntityManager manager = DBService.openDBConnection();
		Query query =  manager.createQuery("SELECT t FROM Tourist t where id=" + touristid);
		Tourist tourist = (Tourist) query.getSingleResult();
		Query query2 =  manager.createQuery("SELECT f FROM Flight f where id=" + flightid);
		Flight flight = (Flight) query2.getSingleResult();			
		Query query3 = manager.createQuery("Select tf.seat FROM TouristFlight tf where flight=" + flightid);
		List<String>seats = query3.getResultList();
		List<String>freeSeats = DBService.fillSeatsNames();
		freeSeats.removeAll(seats);
		model.addAttribute("count", freeSeats.size());
		DBService.closeDBConnection(manager);
		session.setAttribute("tourist", tourist);
		session.setAttribute("flight", flight);
		model.addAttribute("seats", freeSeats);
		return "addTouristFlight";		
	}
	
	@GetMapping("addSeat")
	public String add(@RequestParam("seat")String seat,Model model,HttpSession session) 
	{			
		if(seat==null || seat.equals("" ))return "redirect:/seats/addSeatForm";
		Tourist tourist = (Tourist) session.getAttribute("tourist");
		Flight flight = (Flight) session.getAttribute("flight");
		EntityManager manager = DBService.openDBConnection();
		TouristFlight tf = new TouristFlight(flight, tourist, seat);		
		manager.merge(tf);		
		DBService.closeDBConnection(manager);
		return "redirect:/flights";		
	}
	
	
	
	
	
	
	
}
