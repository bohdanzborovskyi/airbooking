package com.zbodya.model;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TouristRepository extends CrudRepository<Tourist,Long>
{
	@Query(value = "select * from Tourist t where t.country like %:keyword% or t.surname like %:keyword% or t.name like %:keyword%" , nativeQuery = true)
	List<Tourist> findByNameOrSurnameOrCountryOrGenderContaining(@Param("keyword")String keyword);
	List<Tourist> findByCountryContaining(String keyword);
}
