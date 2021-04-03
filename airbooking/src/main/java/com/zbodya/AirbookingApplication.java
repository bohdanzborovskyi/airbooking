package com.zbodya;

import javax.persistence.EntityManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zbodya.service.DBService;


@SpringBootApplication
public class AirbookingApplication {	
	
	public static void main(String[] args)
	{
		SpringApplication.run(AirbookingApplication.class, args);
		
//		EntityManager entityManager = DBService.openDBConnection();
//		DBService.fillDatabase(entityManager);
//		DBService.closeDBConnection(entityManager);
	}

}
