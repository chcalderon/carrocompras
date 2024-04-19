package com.backprinc.rps.backcartapp.services;

import java.util.List;

import com.backprinc.rps.backcartapp.models.entities.Product;

public interface ProductService {
    List<Product> findAll();
}
