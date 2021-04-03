package com.zbodya.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TOURIST_FLIGHT")
public class TouristFlight 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "flight_id")
	private Flight flight;
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "tourist_id")
	private Tourist tourist;
	
	@Column(name = "seat")
	private String seat;
	
	
	
	public TouristFlight() {
		super();
	}

	public TouristFlight(Flight flight, Tourist tourist, String seat) {
		super();		
		this.flight = flight;
		this.tourist = tourist;
		this.seat = seat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Tourist getTourist() {
		return tourist;
	}

	public void setTourist(Tourist tourist) {
		this.tourist = tourist;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	
}
