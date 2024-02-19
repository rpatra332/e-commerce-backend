package com.rp.ecommercebackend.model.dao;

import com.rp.ecommercebackend.model.LocalUser;
import com.rp.ecommercebackend.model.WebOrder;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface WebOrderDAO extends ListCrudRepository<WebOrder, Long> {
    List<WebOrder> findByUser(LocalUser user);
}