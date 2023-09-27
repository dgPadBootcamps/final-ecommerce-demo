package com.dgpad.ecommerceuserdemo.service;

import com.dgpad.ecommerceuserdemo.model.Product;
import com.dgpad.ecommerceuserdemo.model.User;
import com.dgpad.ecommerceuserdemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    private ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Optional<Product> findProductByName(String name){
        return productRepository.findProductByName(name);
    }

    public Boolean productNameExist(String name){ return productRepository.existsByName(name);}

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }
}
