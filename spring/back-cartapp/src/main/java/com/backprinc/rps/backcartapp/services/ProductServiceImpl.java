package com.backprinc.rps.backcartapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backprinc.rps.backcartapp.models.entities.Product;
import com.backprinc.rps.backcartapp.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>)repository.findAll();
    }

}
