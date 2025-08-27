package com.example.demo.config;

import com.example.demo.dao.CustomerRepository;
import com.example.demo.dao.DivisionRepository;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Division;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerSeedConfig {

    // Create a Spring bean of type CommandLineRunner amd Spring injects customers and divisions into the method.
    @Bean
    CommandLineRunner seedCustomers(CustomerRepository customers, DivisionRepository divisions) {
    //  Return args and implement the CommandLineRunner. Spring will execute this block after the app starts.
        return args -> {
            // Load all divisions and grab the first stream
            Long divisionId = divisions.findAll().stream()
                    .findFirst()
                    // Extract Id
                    .map(Division::getId)
                    // Returns null if there are no divisions in the DB.
                    .orElse(null);
//            // If there are no divisions log and skip the seeding customers to not violate the NOT NULL Foreign Key in division_id
//            if (divisionId == null) {
//                System.out.println("Division not found");
//                return;
//            }
            // Seed 5 customers
            // Changed 5 customers phone numbers to include area codes and made their addresses and post codes and numbers match (ex. 100, 11111, 111-123-1234)
            seed(customers, divisionId, "Alvin", "Adams", "100 Alpha St", "11111", "111-123-1234");
            seed(customers, divisionId,"Benjamin", "Burns", "200 Beta St", "22222", "222-234-2345");
            seed(customers, divisionId, "Cindy", "Cross", "300 Colgate St", "33333", "333-345-3456");
            seed(customers, divisionId, "Diana", "Dash", "400 Devonshire St", "44444", "444-456-4567");
            seed(customers, divisionId,"Elle", "Evans", "500 Emory St", "55555", "555-567-5678");
        };
    }
        // Call a helper method to insert each customer only if they do not exist already.
        // If a row with this phone already exists then do nothing. can run multiple times and prevent duplicates
        private void seed(CustomerRepository repo, Long divisionId, String first, String last, String address, String postal, String phone) {
            if (repo.existsByPhone(phone)) {
                System.out.println("Passed over existing customer by phone already in use" + phone);
                return;
            }

            // Build a new Customer and set fields.
            Customer c = new Customer();
            c.setFirstName(first);
            c.setLastName(last);
            c.setAddress(address);
            c.setPostalCode(postal);
            c.setPhone(phone);

            // Only attach a division if one exists
            if (divisionId != null) {
                Division d = new Division();
                d.setId(divisionId);
                c.setDivision(d);
            }
//            // Create a Division reference with just the ID set. JPA will write division_id on customer.
//            Division d = new Division();
//            // Attach existing Division by id
//            d.setId(divisionId);
//            c.setDivision(d);

            repo.save(c);
            System.out.println("Added customer: " + first + " " + last);
        }
    }
