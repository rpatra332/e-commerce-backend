package com.rp.ecommercebackend.service;

import com.rp.ecommercebackend.api.model.RegistrationBody;
import com.rp.ecommercebackend.exception.UserAlreadyExistsException;
import com.rp.ecommercebackend.model.LocalUser;
import com.rp.ecommercebackend.model.dao.LocalUserDAO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private LocalUserDAO userDAO;

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
        LocalUser user = new LocalUser();
        if (userDAO.findByEmailIgnoreCase(registrationBody.email()).isPresent()
                || userDAO.findByUsernameIgnoreCase(registrationBody.username()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        user.setUsername(registrationBody.username());
        user.setFirstName(registrationBody.firstName());
        user.setLastName(registrationBody.lastName());
        user.setEmail(registrationBody.email());
        //TODO: Encrypt Password
        user.setPassword(registrationBody.password());
        return userDAO.save(user);
    }
}
