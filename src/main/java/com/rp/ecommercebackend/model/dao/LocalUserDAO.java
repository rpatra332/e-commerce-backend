package com.rp.ecommercebackend.model.dao;

import com.rp.ecommercebackend.model.LocalUser;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface LocalUserDAO extends ListCrudRepository<LocalUser, Long> {
    Optional<LocalUser> findByUsernameIgnoreCase(String username);

    Optional<LocalUser> findByEmailIgnoreCase(String email);
}
