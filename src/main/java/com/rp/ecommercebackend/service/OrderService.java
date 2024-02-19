package com.rp.ecommercebackend.service;

import com.rp.ecommercebackend.model.LocalUser;
import com.rp.ecommercebackend.model.WebOrder;
import com.rp.ecommercebackend.model.dao.WebOrderDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private WebOrderDAO webOrderDAO;

    public List<WebOrder> getOrders(LocalUser user) {
        return webOrderDAO.findByUser(user);
    }
}
