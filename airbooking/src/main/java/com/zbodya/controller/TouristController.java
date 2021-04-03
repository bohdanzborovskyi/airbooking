package com.zbodya.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.zbodya.model.Tourist;
import com.zbodya.service.DBService;
import com.zbodya.service.TouristService;

@Controller
@RequestMapping("tourists")
public class TouristController 
{
	
	@Autowired 
	TouristService tservice;
	private Integer currentPage;

	@GetMapping()
	public String getAllTourists(Model model, @RequestParam("size") Optional<Integer> size,@RequestParam("page")Optional<Integer> page) 
	{
	
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		
		Page<Tourist> touristPage = tservice.findPaginated(PageRequest.of(currentPage-1, pageSize));		
		model.addAttribute("tourists", touristPage);
		
		if(touristPage.getTotalPages()>0) 
		{
			List<Integer> pageNumbers = IntStream.rangeClosed(1,touristPage.getTotalPages()).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers.size());
			model.addAttribute("page",currentPage-1);
		}
		return "tourists";
	}
	
	@GetMapping("sortByUsername")
	public String getAllTouristsByUsername(Model model, @RequestParam("size") Optional<Integer> size,@RequestParam("page")Optional<Integer> page) 
	{
	
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		
		Page<Tourist> touristPage = tservice.findPaginatedSortBySurname(PageRequest.of(currentPage-1, pageSize));		
		model.addAttribute("tourists", touristPage);
		
		if(touristPage.getTotalPages()>0) 
		{
			List<Integer> pageNumbers = IntStream.rangeClosed(1,touristPage.getTotalPages()).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers.size());
			model.addAttribute("page",currentPage-1);
		}
		model.addAttribute("sort","/sortByUsername");
		return "tourists";
	}
	
	@GetMapping("search")
	public String searchByKeyword(@RequestParam("keyword")Optional<String> keyword, @RequestParam("size") Optional<Integer> size,@RequestParam("page")Optional<Integer> page, Model model) 
	{
		String search;
		System.out.println("Keyword: " + model.getAttribute("keyword"));
		if(keyword==null && model.getAttribute("keyword")!=null)  search = (String) model.getAttribute("keyword");
		else  search = keyword.orElse("");
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		
		Page<Tourist> touristPage = tservice.findPaginatedSearched(PageRequest.of(currentPage-1, pageSize),search);		
		model.addAttribute("tourists", touristPage);
		
		if(touristPage.getTotalPages()>0) 
		{
			List<Integer> pageNumbers = IntStream.rangeClosed(1,touristPage.getTotalPages()).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers.size());
				
		}
		model.addAttribute("page",currentPage-1);		
		model.addAttribute("sort","/search");
		model.addAttribute("keyword", search);
		return "tourists";
	}
	
	@GetMapping("sortByCountry")
	public String getAllTouristsByCountry(Model model, @RequestParam("size") Optional<Integer> size,@RequestParam("page")Optional<Integer> page) 
	{
	
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		
		Page<Tourist> touristPage = tservice.findPaginatedSortByCountry(PageRequest.of(currentPage-1, pageSize));		
		model.addAttribute("tourists", touristPage);
		
		if(touristPage.getTotalPages()>0) 
		{
			List<Integer> pageNumbers = IntStream.rangeClosed(1,touristPage.getTotalPages()).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers.size());
		}
		model.addAttribute("sort","/sortByCountry");
		model.addAttribute("page",currentPage-1);
		return "tourists";
	}
	
	@GetMapping("sortByBirthday")
	public String getAllTouristsByBirthday(Model model, @RequestParam("size") Optional<Integer> size,@RequestParam("page")Optional<Integer> page) 
	{
	
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		
		Page<Tourist> touristPage = tservice.findPaginatedSortByBirthday(PageRequest.of(currentPage-1, pageSize));		
		model.addAttribute("tourists", touristPage);
		
		if(touristPage.getTotalPages()>0) 
		{
			List<Integer> pageNumbers = IntStream.rangeClosed(1,touristPage.getTotalPages()).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers.size());
		}
		model.addAttribute("sort","/sortByBirthday");
		model.addAttribute("page",currentPage-1);
		return "tourists";
	}
	
	@GetMapping("delete/{id}")
	public String deleteTourist(@PathVariable("id")long id, Model model) 
	{
		EntityManager manager = DBService.openDBConnection();		
		Query findTourist = manager.createQuery("SELECT t FROM Tourist t WHERE t.id= " +  id );
		Tourist tourist = (Tourist) findTourist.getSingleResult();
		manager.remove(tourist);				
		DBService.closeDBConnection(manager);	
		return "redirect:/tourists";
	}
	
	@GetMapping("addTouristForm")
	public String addTouristForm(Tourist tourist)
	{
		return "addTouristForm";
	}
	
	@PostMapping("addTourist")
	public String addTourist(@Valid  Tourist tourist, BindingResult bindingResult, Model model) 
	{
		if(bindingResult.hasErrors())
		{	
			System.out.println("BINNNNNN");
			return "addTouristForm";
		}
		else 
		{
		EntityManager manager = DBService.openDBConnection();		
		manager.persist(tourist);		
		DBService.closeDBConnection(manager);
		return "redirect:/tourists";
		}		
	}
	
	@GetMapping("editTourist/{id}")
	public String editTourist(@PathVariable long id, Model model) 
	{
		EntityManager entityManager = DBService.openDBConnection();							
		Query query =  entityManager.createQuery("SELECT t FROM Tourist t WHERE id =" + id );
		Tourist tourist =(Tourist) query.getSingleResult();		
		DBService.closeDBConnection(entityManager);
		model.addAttribute("tourist", tourist);
		return "updateTourist";
	}
	
	@PostMapping("updateTourist/{id}")
	public String updateTourist(@PathVariable long id, @Valid Tourist tourist,BindingResult bindingResult, Model model) 
	{
		if(bindingResult.hasErrors())return "updateTourist";
		else 
		{
		EntityManager entityManager = DBService.openDBConnection();	
		entityManager.merge(tourist);
		DBService.closeDBConnection(entityManager);	
		return "redirect:/tourists";
		}
	}	
}
