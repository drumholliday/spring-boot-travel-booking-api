package com.example.demo.entities;

// JPA annotations

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
// import java.util.Set; // Uncomment later when CartItem exists

@Entity // Maps this class to a DB table
@Table(name = "carts") // Explicit table name
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Cart {
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", nullable = false)
    private Long id;

    @Column(name = "order_tracking_number", length = 64)
    private String orderTrackingNumber;

    // Total package price for this cart use BigDecimal since it is money
    @Column(name = "package_price", precision = 12, scale = 2)
    private BigDecimal package_price;

    // Party size associated with this cart/purchase
    @Column(name = "party_size")
    private Integer party_size;

    // Enum mapped as a string column (pending/ordered/cancelled)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private StatusType status;

    // Auto-managed timestamps
    @Column(name = "create_date")
    @CreationTimestamp
    private Date create_date;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Date last_update;

    // Many carts belong to one customer
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Optional FK mirror: direct read-only access to customer_id
    // Maps to same column as @JoinColumn above but is not written by JPA.
    // Useful for quick reads/logs without loading Customer.
    // IMPORTANT: Do not set customer_ID directly; set the relationship via 'customer'.
    @Column(name = "customer_id", insertable = false, updatable = false)
    private Long customer_ID;

    // CartItem (from UML: cart -> cartItem)
    // Uncomment after CartItem.java exists
    // @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private Set<CartItem> cartItem;
}
