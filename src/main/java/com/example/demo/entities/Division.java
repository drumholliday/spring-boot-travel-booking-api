package com.example.demo.entities;

// Import used tp bring in annotations (@Entity, @ID etc..) that map the class to a database table
import jakarta.persistence.*;

// ADDED Json Imports
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
import java.util.Set;

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

    // Maps to division column
    // CHANGED name = division_name to division to match DB
    @Column(name = "division")
//    private String division_name;
    // CHANGED TO name
    private String name;

    // ADDED output alias so frontend can read division if it expects that key
    @JsonProperty("division")
    public String getDivisionLabel() {
        return name;
    }

    // ADDED compatibility for division.division_name
    @JsonProperty("division_name")
    public String getDivisionNameTwo() { return name; }

    @JsonAlias({"division_name"})
    public void setDivisionNameTwo(String v) { this.name = v; }

    // ADDED to accept either division or name on input which maps to name
    @JsonAlias({"division"})
    public void setDivisionLabel(String value) {
        this.name = value;
    }

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
    // ADDED JsonIgnore to tell Jackson to Ignore the lazy country field when serializing a Division.
    @JsonIgnore
    private Country country;

    // Expose the Foreign Key as a read only scalar to match the UML's country_ID
    // This maps tp the same column as @JoinColumn on 'country' but isn't written by JPA.
    // It mirrors the Foreign Key value for quick reads/logs w/o forcing a lazy load of Country object.
    // This matches the UML diagram's explicit country_ID field.
    // Set insertable = false and updatable = false makes it read only.
    // *** Don't set country_ID directly, ALWAYS set the relationship via country.
    // The country_ID value will reflect the Foreign Key stored in the DB after persistence.
    // ADDED JsonProperty to emit JSON as country_id
    @JsonProperty("country_id")
    // ADDED JsonAlias to accept either country_ID or country_id
    @JsonAlias({"country_ID"})
    @Column(name = "country_id", insertable = false, updatable = false) // Direct access to FK column from Division class.
    private Long country_ID;

    @OneToMany(mappedBy = "division", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // ADDED JsonIgnore b/c Jackson tries to serialize all properties of Division. Tells Jackson to ignore customers.
    @JsonIgnore
    private Set<Customer> customers;
}
