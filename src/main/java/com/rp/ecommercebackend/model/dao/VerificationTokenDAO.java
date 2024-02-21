package com.rp.ecommercebackend.model.dao;

import com.rp.ecommercebackend.model.LocalUser;
import com.rp.ecommercebackend.model.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface VerificationTokenDAO extends ListCrudRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);

    void deleteByUser(LocalUser user);
}