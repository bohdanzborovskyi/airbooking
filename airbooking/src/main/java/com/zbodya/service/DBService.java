package com.zbodya.service;


import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Service;

import com.zbodya.model.Flight;
import com.zbodya.model.Tourist;
import com.zbodya.model.TouristFlight;

@Service
public class DBService 
{
	public static EntityManagerFactory entityManagerFactory;
	public static EntityManager entityManager;
	public static List<String> seatsNames;
	
	public static EntityManager openDBConnection() 
	{
		entityManagerFactory = Persistence.createEntityManagerFactory("com.zbodya");
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		seatsNames = fillSeatsNames();		
		return entityManager;
	}
	
	public static void closeDBConnection(EntityManager entityManager) 
	{
		entityManager.getTransaction().commit();		
		entityManager.close();	
		entityManagerFactory.close();
	}
	
	public static void fillDatabase(EntityManager manager) 
	{		
		Tourist t = new Tourist("Jackob","Spirs","male","Germany","notes",Date.valueOf("1995-1-19"));
		Tourist t1 = new Tourist("Jacks","Lebdo","male","Poland","notes",Date.valueOf("2012-1-19"));
		Tourist t2 = new Tourist("Angello","Walker","male","Brazil","notes",Date.valueOf("1943-1-19"));
		Tourist t3 = new Tourist("David","Angello","male","USA","notes",Date.valueOf("1965-1-19"));
		Tourist t4 = new Tourist("Fore","Gabriel","male","China","notes",Date.valueOf("1997-1-19"));
		Tourist t5 = new Tourist("Lebo","Lebdi","male","Belgium","notes",Date.valueOf("1987-1-19"));
		Tourist t6 = new Tourist("Lebo","Oal","male","Ukraine","notes",Date.valueOf("1993-1-19"));
		Flight f = new Flight(LocalDateTime.of(2020, 3, 4, 16, 00),LocalDateTime.of(2020, 3, 4, 16, 30),30,400);
		Flight f1 = new Flight(LocalDateTime.of(2020, 3, 6, 18, 00),LocalDateTime.of(2020, 3, 6, 20, 30),30,800);
		Flight f2 = new Flight(LocalDateTime.of(2020, 3, 7, 12, 40),LocalDateTime.of(2020, 3, 7, 16, 15),30,1200);
		Flight f3 = new Flight(LocalDateTime.of(2020, 3, 5, 21, 45),LocalDateTime.of(2020, 3, 6, 01, 30),30,700);
		Flight f4 = new Flight(LocalDateTime.of(2020, 3, 7, 10, 20),LocalDateTime.of(2020, 3, 7, 14, 40),30,2000);
		Flight f5 = new Flight(LocalDateTime.of(2020, 3, 8, 19, 35),LocalDateTime.of(2020, 3, 9, 00, 30),30,900);
		Flight f6 = new Flight(LocalDateTime.of(2020, 3, 9, 23, 40),LocalDateTime.of(2020, 3, 10, 04, 30),30,1800);
		TouristFlight tf = new TouristFlight(f,t,"A1");
		TouristFlight tf1 = new TouristFlight(f1,t1,"A1");
		TouristFlight tf2 = new TouristFlight(f2,t2,"A1");
		TouristFlight tf3 = new TouristFlight(f3,t3,"A1");
		TouristFlight tf4 = new TouristFlight(f4,t4,"A1");
		TouristFlight tf5 = new TouristFlight(f5,t5,"A1");
		TouristFlight tf6 = new TouristFlight(f6,t6,"A1");
		TouristFlight tf7 = new TouristFlight(f3,t4,"A1");
		TouristFlight tf8 = new TouristFlight(f2,t3,"A1");		
		manager.persist(f);
		manager.persist(t);
		manager.persist(t1);
		manager.persist(t2);
		manager.persist(t3);
		manager.persist(t4);
		manager.persist(t5);
		manager.persist(f2);
		manager.persist(f3);
		manager.persist(f4);
		manager.persist(f5);
		manager.persist(f6);
		manager.persist(t6);
		manager.persist(tf);
		manager.persist(tf1);
		manager.persist(tf2);
		manager.persist(tf3);
		manager.persist(tf4);
		manager.persist(tf5);
		manager.persist(tf6);
		manager.persist(tf7);
		manager.persist(tf8);
	}
	
	public static List<String> fillSeatsNames()
	{
		List<String> names = new ArrayList<>();
		for(int i=0;i<31;i++) 
		{
			names.add("A" + i);
			names.add("B" + i);
			names.add("C" + i);
			names.add("D" + i);
			names.add("E" + i);
			names.add("F" + i);
		}
		return names;
	}

}
