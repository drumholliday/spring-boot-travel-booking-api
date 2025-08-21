package com.example.demo.entities;

// JPA annotations
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

// Import HashSet
import java.util.HashSet;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity // Maps this class to a DB table
@Access(AccessType.FIELD) // Tells Hibernate to ignore getters/setters for persistence state
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

    // Order tracking number with 64 length to leave enough room for longer tracking numbers.
    @Column(name = "order_tracking_number", length = 64)
    private String orderTrackingNumber;

    // Total package price for this cart and using BigDecimal since it is money
    @Column(name = "package_price", precision = 12, scale = 2)
    private BigDecimal package_price;

    // Party size for this cart/purchase
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

//    // Maps to same column as @JoinColumn above but is not written by JPA.
//    // *** Don't set customer_ID directly; set the relationship via 'customer'.
//    @Column(name = "customer_id", insertable = false, updatable = false)
//    private Long customer_ID;

//    // Try Cascade from Cart (not saveAll)
//    @OneToMany(mappedBy = "cart", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
//    private Set<CartItem> cartItem = new HashSet<>();

//    Replace Cascade with explicit save path
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<CartItem> cartItem = new HashSet<>();

    // Helper method to keep both sides in sync
    public void addItem(CartItem item) {
        if (item == null) return;
        cartItem.add(item);
        item.setCart(this);
    }

    // Comment out Cascade all for now and remove after testing if problem is solved.
//    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<CartItem> cartItem;
}
