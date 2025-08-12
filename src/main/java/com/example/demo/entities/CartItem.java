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

@Entity // Marks this class as a JPA entity
@Table(name = "cart_items") // Explicitly names the table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class CartItem {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id", nullable = false)
    private Long id;

    // Price for this cart item (BigDecimal for money)
    @Column(name = "excursion_price", precision = 12, scale = 2)
    private BigDecimal excursion_price;

    // Quantity in the cart
    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "create_date")
    @CreationTimestamp
    private Date create_date;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Date last_update;

    // Many cart_items belong to one cart
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    // Read-only mirror of FK (cart_id)
    @Column(name = "cart_id", insertable = false, updatable = false)
    private Long cart_ID;

    // Many cart items relate to one excursion
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "excursion_id", nullable = false)
    private Excursion excursion;

    // Read-only mirror of Foreign Key (excursion_id)
    @Column(name = "excursion_id", insertable = false, updatable = false)
    private Long excursion_ID;
}
