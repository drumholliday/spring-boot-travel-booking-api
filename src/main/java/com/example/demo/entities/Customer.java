package com.example.demo.entities;

// Import used to bring in annotations (@Entity, @Id, etc.) that map the class to a database table
import jakarta.persistence.*;

// Lombok annotations to auto-generate constructors, getters, and setters
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Hibernate helpers for auto-filling timestamp fields when a row is created or updated
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
// import java.util.Set; // Will be used later for carts

@Entity // Marks this class as a JPA entity
@Table(name = "customers") // Explicitly names the table as 'customers'
@NoArgsConstructor // Lombok: generates a no-arg constructor
@AllArgsConstructor // Lombok: generates an all-arg constructor
@Getter // Lombok: generates getters
@Setter // Lombok: generates setters

public class Customer {
    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increments in DB
    @Column(name = "customer_id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "postal_code", length = 20)
    private String postal_code;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "create_date")
    @CreationTimestamp // Hibernate sets create_date on first insert
    private Date create_date;

    @Column(name = "last_update")
    @UpdateTimestamp // Hibernate updates when the row changes
    private Date last_update;

    // Many customers belong to one division
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "division_id", nullable = false)
    private Division division;

    // Mirrors the same column as @JoinColumn above, but isn't written by JPA
    // Good for quick reads/logs w/o forcing a lazy load of Division
    // Matches UML's explicit division_ID field
    // *** DO NOT set division_ID directly, ALWAYS set the relationship via 'division'.
    @Column(name = "division_id", insertable = false, updatable = false)
    private Long division_ID;

    // One to many Carts
    // From UML customers -> carts
    // Uncomment after Cart.java is created to prevent any issues
//    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<Cart> carts;


}
