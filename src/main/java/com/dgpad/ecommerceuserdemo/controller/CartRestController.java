package com.dgpad.ecommerceuserdemo.controller;

import com.dgpad.ecommerceuserdemo.model.CartItem;
import com.dgpad.ecommerceuserdemo.model.Product;
import com.dgpad.ecommerceuserdemo.model.User;
import com.dgpad.ecommerceuserdemo.security.UserInfoDetails;
import com.dgpad.ecommerceuserdemo.service.CartItemService;
import com.dgpad.ecommerceuserdemo.service.ProductService;
import com.dgpad.ecommerceuserdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController()
@RequestMapping("/cart")
public class CartRestController {

    private final CartItemService cartItemService;
    private final ProductService productService;
    private final UserService userService;


    //this is a comment
    @Autowired
    public CartRestController(CartItemService cartItemService, ProductService productService, UserService userService){
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping(value = "/add-item",  produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> createCartItem(@RequestParam Long productId, @RequestParam Integer quantity, Authentication authentication){
        if(Objects.isNull(productService.getProductById(productId)))
            return new ResponseEntity<>("Product is Not Available", HttpStatus.CONFLICT);

        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;

            User user = userService.getUserById(userInfoDetails.getUserId());
            Product product = productService.getProductById(productId);

            return new ResponseEntity<>(cartItemService.createCartItem(new CartItem(user,product,quantity)), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/",  produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> getCartItems(){
        try{
            return new ResponseEntity<>(cartItemService.getCartItems(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/get-user-cart",  produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> getCartItems(Authentication authentication){
        try{

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;

            return new ResponseEntity<>(cartItemService.getUserCartItems(userInfoDetails.getUserId()), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/delete/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> deleteCartItem(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<>(cartItemService.deleteCartItem(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
