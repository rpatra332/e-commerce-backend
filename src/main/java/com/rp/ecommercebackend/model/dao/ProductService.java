package com.rp.ecommercebackend.model.dao;

import com.rp.ecommercebackend.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductDAO productDAO;

    public List<Product> getProducts() {
        return productDAO.findAll();
    }
}
