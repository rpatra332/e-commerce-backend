package com.rp.ecommercebackend.model.dao;

import com.rp.ecommercebackend.model.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductDAO extends ListCrudRepository<Product, Long> {
}