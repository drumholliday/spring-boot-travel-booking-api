package com.example.demo.dao;

import com.example.demo.entities.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// ADDED
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.jpa.repository.Query;

@CrossOrigin("http://localhost:4200")
@RepositoryRestResource(collectionResourceRel = "divisions", path = "divisions")
public interface DivisionRepository extends JpaRepository<Division, Long> {
    // -> /api/divisions/search/findByCountryId?id=1
    @RestResource(path = "findByCountry_Id", rel = "findByCountry_Id")
    Page<Division> findByCountry_Id(@Param("id") Long id, Pageable pageable);

    // Alias that makes frontend call
    // URL http://localhost:8080/api/divisions/search/findByCountryId?id=1
    @RestResource(path = "findByCountryId", rel = "findByCountryId")
    @Query("select d from Division d where d.country.id = :id order by d.name asc")
    Page<Division> findByCountryIdAlias(@Param("id") Long id, Pageable pageable);
}