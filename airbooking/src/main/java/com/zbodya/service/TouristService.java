package com.zbodya.service;

import java.util.Collections;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.zbodya.model.Tourist;
import com.zbodya.model.TouristRepository;
import com.zbodya.model.TouristSortAndPageRepository;

@Service
public class TouristService 
{
	@Autowired
	TouristSortAndPageRepository sprepo;
	
	@Autowired
	TouristRepository repo;			
	
	public Page<Tourist> findPaginated(Pageable pageable)
	{
		List<Tourist>tourists = (List<Tourist>) repo.findAll();
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Tourist>list;
		
		if(tourists.size()<startItem) 
		{
			list = Collections.EMPTY_LIST;
		}else 
		{
			int toIndex = Math.min(startItem + pageSize, tourists.size());
			list = tourists.subList(startItem,toIndex);
		}
		
		Page<Tourist> touristPage = new PageImpl<Tourist>(list,PageRequest.of(currentPage,pageSize),tourists.size());
		
		return touristPage;
		
	}
	
	public Page<Tourist> findPaginatedSortBySurname(Pageable pageable)
	{
		List<Tourist>tourists = (List<Tourist>) repo.findAll();
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Tourist>list;
		
		if(tourists.size()<startItem) 
		{
			list = Collections.EMPTY_LIST;
		}else 
		{
			int toIndex = Math.min(startItem + pageSize, tourists.size());
			list = tourists.subList(startItem,toIndex);
		}
		
		Pageable sortedBySurname = PageRequest.of(currentPage, pageSize,Sort.by("surname"));
		Page<Tourist> touristPage = sprepo.findAll(sortedBySurname);
		
		return touristPage;
	}
	
	public Page<Tourist> findPaginatedSortByCountry(Pageable pageable)
	{
		List<Tourist>tourists = (List<Tourist>) repo.findAll();
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Tourist>list;
		
		if(tourists.size()<startItem) 
		{
			list = Collections.EMPTY_LIST;
		}else 
		{
			int toIndex = Math.min(startItem + pageSize, tourists.size());
			list = tourists.subList(startItem,toIndex);
		}
		
		Pageable sortedByCountry = PageRequest.of(currentPage, pageSize,Sort.by("country"));
		Page<Tourist> touristPage = sprepo.findAll(sortedByCountry);
		
		return touristPage;
	}
	
	public Page<Tourist> findPaginatedSortByBirthday(Pageable pageable)
	{
		List<Tourist>tourists = (List<Tourist>) repo.findAll();
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Tourist>list;
		
		if(tourists.size()<startItem) 
		{
			list = Collections.EMPTY_LIST;
		}else 
		{
			int toIndex = Math.min(startItem + pageSize, tourists.size());
			list = tourists.subList(startItem,toIndex);
		}
		
		Pageable sortedByBirthday = PageRequest.of(currentPage, pageSize,Sort.by("birthday"));
		Page<Tourist> touristPage = sprepo.findAll(sortedByBirthday);
		
		return touristPage;
	}
	
	public Page<Tourist> findPaginatedSearched(Pageable pageable,String search)
	{
		List<Tourist>tourists = (List<Tourist>) repo.findByNameOrSurnameOrCountryOrGenderContaining(search);

		System.out.println("Tourists : " + tourists);
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Tourist>list;
		
		if(tourists.size()<startItem) 
		{
			list = Collections.EMPTY_LIST;
		}else 
		{
			int toIndex = Math.min(startItem + pageSize, tourists.size());
			list = tourists.subList(startItem,toIndex);
		}
		
		Page<Tourist> touristPage = new PageImpl<Tourist>(list,PageRequest.of(currentPage,pageSize),tourists.size());
		
		return touristPage;
		
	}
}

