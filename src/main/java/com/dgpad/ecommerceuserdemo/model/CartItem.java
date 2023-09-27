package com.dgpad.ecommerceuserdemo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Product product;
    private int quantity;

    public CartItem(User user, Product product, Integer quantity){
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }
}
