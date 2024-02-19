package com.rp.ecommercebackend.api.controller.order;

import com.rp.ecommercebackend.model.LocalUser;
import com.rp.ecommercebackend.model.WebOrder;
import com.rp.ecommercebackend.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;

    @GetMapping
    public List<WebOrder> getOrders(@AuthenticationPrincipal LocalUser user) {
        return orderService.getOrders(user);
    }
}
