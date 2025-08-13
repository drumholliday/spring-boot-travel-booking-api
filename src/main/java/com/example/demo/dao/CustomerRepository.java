package com.example.demo.dao;

import com.example.demo.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@CrossOrigin("http://localhost:4200")
@RepositoryRestResource(collectionResourceRel = "customers", path = "customers")
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Create an existence check to the repo using a boolean amd avoid duplicates.
    boolean existsByPhone(String phone);
}