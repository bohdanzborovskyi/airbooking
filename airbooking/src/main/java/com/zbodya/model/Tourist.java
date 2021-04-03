package com.zbodya.model;

import java.sql.Date;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "TOURIST")
public class Tourist 
{ 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@NotBlank(message = "Please insert your name")
	@Column(name = "name")
	private String name;	

	@NotBlank(message = "Please insert your surname")
	@Column(name = "surname")
	private String surname;
	
	@NotBlank(message = "Please insert your gender")
	@Column(name = "gender")
	private String gender;
	
	@NotBlank(message = "Please insert your country")
	@Column(name = "country")
	private String country;
	
	@Size(min=0,max=100, message = "This text is so big")
	@Column(name = "notes")
	private String notes;
	
	@NotNull
	@Column(name = "birthday")
	private Date birthday;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tourist")
	private List<TouristFlight> seats;	

	public Tourist(String name, String surname, String gender, String country, String notes, Date birthday) {
		super();
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.country = country;
		this.notes = notes;
		this.birthday = birthday;	
		this.seats = new ArrayList<TouristFlight>();
	}

	public Tourist() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public List<TouristFlight> getFlights() {
		return seats;
	}

	public void setFlights(List<TouristFlight> flights) {
		this.seats = flights;
	}	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void deleteSeatByTouristID(int touristID) 
	{
		for(TouristFlight tf : this.seats) 
		{
			if(tf.getTourist().getId()==touristID) 
			{
				this.seats.remove(tf);
			}
		}
	}
	
	
}

