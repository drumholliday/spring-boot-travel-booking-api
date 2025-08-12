package com.example.demo.entities;

// Import used tp bring in annotations (@Entity, @ID etc..) that map the class to a database table
import jakarta.persistence.*;

// Import Lombok annotations to auto-generate constructors, getters, and setters.
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Hibernate helpers that autofill timestamp fields when a row is created or updated.
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

// Import Date (java type for timestamp columns)
import java.util.Date;
import java.util.Set; //  Set will be used later when Customers is created.

@Entity // tells Java Persistence API (JPA) this class maps to a table.
@Table(name = "divisions") // explicitly names the table (divisions)
@NoArgsConstructor //Lombok generates a no-arg constructor
@AllArgsConstructor // Lombok generates an all-arg constructor.
@Getter // Lombok generates getters for all fields.
@Setter // Lombok generates setters for all fields.

public class Division {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB auto-increments the ID (common with MySQL/H2).
    @Column(name = "division_id", nullable = false) // maps the field to the division_id column and enforces not-null.
    private Long id;

    // Maps to division_name column
    @Column(name = "division_name")
    private String division_name;

    @Column(name = "create_date")
    @CreationTimestamp // Hibernate sets create_date when the row is first inserted.
    private Date create_date;

    @Column(name = "last_update")
    @UpdateTimestamp // Hibernate updates last_update every time the row changes.
    private Date last_update;

    // Many divisions belong to one country. Each division references one country.
    // FetchType.Lazy so country object is loaded only when accessed, not every time division is fetched.
    // Optional is false so DB level is not null and every Division must have a Country.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)

    // The Foreign Key column in divisions table is country_id, linking to countries.country_id from the Country Entity.
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    // Expose the Foreign Key as a read only scalar to match the UML's country_ID
    // This maps tp the same column as @JoinColumn on 'country' but isn't written by JPA.
    // It mirrors the Foreign Key value for quick reads/logs w/o forcing a lazy load of Country object.
    // This matches the UML diagram's explicit country_ID field.
    // Set insertable = false and updatable = false makes it read only.
    // *** Don't set country_ID directly, ALWAYS set the relationship via country.
    // The country_ID value will reflect the Foreign Key stored in the DB after persistence.
    @Column(name = "country_id", insertable = false, updatable = false) // Direct access to FK column from Division class.
    private Long country_ID;

    // ** Future one to many customers
    // From UML: divisions -> customers (one division has many customers)
    // Add this AFTER the Customer entity exists, so it compiles cleanly.
     @OneToMany(mappedBy = "division", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
     private Set<Customer> customers;
}
