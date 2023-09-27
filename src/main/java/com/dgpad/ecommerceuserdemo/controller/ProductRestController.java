package com.dgpad.ecommerceuserdemo.controller;

import com.dgpad.ecommerceuserdemo.model.Product;
import com.dgpad.ecommerceuserdemo.model.User;
import com.dgpad.ecommerceuserdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping("/products")
public class ProductRestController {

    private final ProductService productService;

    @Autowired
    private ProductRestController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping(value = "/create",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        if(productService.productNameExist(product.getName()))
            return new ResponseEntity<>("Product Name is Already in Use", HttpStatus.CONFLICT);

        try {
            Product newProduct = productService.createProduct(product);
            return new ResponseEntity<>(newProduct, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProducts(){
        try{
            return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProduct(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

