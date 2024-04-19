package com.backprinc.rps.backcartapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.backprinc.rps.backcartapp.models.entities.Product;
import com.backprinc.rps.backcartapp.services.ProductService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@CrossOrigin(origins = "http://localhost:5174")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public List<Product> list(){
        return service.findAll();
    }
}
