package com.backprinc.rps.backcartapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.backprinc.rps.backcartapp.models.entities.Product;


public interface ProductRepository extends CrudRepository<Product, Long> {

}
