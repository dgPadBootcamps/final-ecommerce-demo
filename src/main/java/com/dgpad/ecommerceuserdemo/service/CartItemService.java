package com.dgpad.ecommerceuserdemo.service;

import com.dgpad.ecommerceuserdemo.model.CartItem;
import com.dgpad.ecommerceuserdemo.model.Product;
import com.dgpad.ecommerceuserdemo.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    @Autowired
    private CartItemService(CartItemRepository cartItemRepository){
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem createCartItem(CartItem cartItem){
        return cartItemRepository.save(cartItem);
    }

    public Long deleteCartItem(Long id){
        cartItemRepository.deleteById(id);
        return id;
    }

    public List<CartItem> getCartItems(){
        return cartItemRepository.findAll();
    }

    public List<CartItem> getUserCartItems(UUID id){
        return cartItemRepository.findCartItemsByUserId(id);
    }
}
