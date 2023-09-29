package com.dgpad.ecommerceuserdemo.model;

import com.dgpad.ecommerceuserdemo.model.DTOs.CartItemDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonIgnore
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
