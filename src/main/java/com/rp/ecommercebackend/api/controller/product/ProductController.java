package com.rp.ecommercebackend.api.controller.product;

import com.rp.ecommercebackend.model.Product;
import com.rp.ecommercebackend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {
    private ProductService productService;

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }
}
