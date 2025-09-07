package com.example.demo.entities;

// JPA annotations

import jakarta.persistence.*;

// Lombok (auto getters/setters/constructors)
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Hibernate timestamps
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "excursions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Excursion {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "excursion_id", nullable = false)
    private Long id;

    // Title/name of the excursion (matches UML)
    @Column(name = "excursion_title")
    private String excursion_title;

    // Price for this excursion (BigDecimal for money)
    // NOTE: UML spells the column 'excusion_price' (missing 'r'); mapping matches UML exactly
    // CHANGED IT to excursion_price and precision to 19.
    @Column(name = "excursion_price", precision = 19, scale = 2)
    private BigDecimal excursion_price;

    // Optional image URL
    // CHANGED from image_URL to image_url
    @Column(name = "image_url")
    private String image_URL;

    // Auto-managed timestamps
    @Column(name = "create_date")
    @CreationTimestamp
    private Date create_date;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Date last_update;

    // Many excursions belong to one vacation
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vacation_id", nullable = false)
    private Vacation vacation;

    // Read-only Foreign Key mirror for vacation_id
    @Column(name = "vacation_id", insertable = false, updatable = false)
    private Long vacation_ID;

    // Back-reference for the many-to-many via excursion_cartitem
    @ManyToMany(mappedBy = "excursions", fetch = FetchType.LAZY)
    private Set<CartItem> cartitems;
}
