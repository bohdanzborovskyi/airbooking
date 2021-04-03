package com.zbodya.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Component;

@Component(value = "flight")
@Entity
@Table(name="FLIGHT")
public class Flight 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;	
	
	@Future(message = "Date must be in the fututre")
	@NotNull(message = "Please insert arrival date and time")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name = "arrival_time")
	private LocalDateTime arrivalTime;
	
	@Future(message = "Date must be in the fututre")
	@NotNull(message = "Please insert departure date and time")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name = "departure_time")
	private LocalDateTime departureTime;
	
	@Column(name = "seats_count")
	private int seatsCount;
	
	@OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
	List<TouristFlight> seats;
	
	@Min(value = 100, message = "Please insert ticket price bigger than 100")
	@NotNull(message = "Please insert ticket price")
	@Column(name = "ticket_price")
	private int ticketPrice;	
	
	
	
	public Flight() {
		super();
	}

	public Flight(LocalDateTime arrivalTime, LocalDateTime departureTime, int seatsCount,int ticketPrice) {
		super();		
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.seatsCount = seatsCount;		
		this.ticketPrice = ticketPrice;
		this.seats = new ArrayList<TouristFlight>();		
	}

	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	public int getSeatsCount() {
		return seatsCount;
	}

	public void setSeatsCount(int seatsCount) {
		this.seatsCount = seatsCount;
	}

	public List<TouristFlight> getTourists() {
		return seats;
	}

	public void setTourists(List<TouristFlight> tourists) {
		this.seats = tourists;
	}

	public int getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(int ticketPrice) {
		this.ticketPrice = ticketPrice;
	}	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void deleteSeatByFlightID(long id2) 
	{
		for(TouristFlight tf : this.seats) 
		{
			if(tf.getFlight().getId()==id2) 
			{
				this.seats.remove(tf);
			}
		}
	}
	
	
	
	
	
	
	
	

}

