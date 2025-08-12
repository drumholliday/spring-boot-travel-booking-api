package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "vacations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id", nullable = false)
    private Long id;

    @Column(name = "vacation_title")
    private String vacation_title;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "travel_fare_price", precision = 19, scale = 2)
    private BigDecimal travel_price;

    @Column(name = "image_url")
    private String image_URL;

    @Column(name = "create_date")
    @CreationTimestamp
    private Date create_date;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Date last_update;

    // One vacation has many excursions
    @OneToMany(mappedBy = "vacation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Excursion> excursions;
}
