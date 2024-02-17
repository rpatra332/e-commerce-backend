package com.rp.ecommercebackend.service;

import com.rp.ecommercebackend.api.model.LoginBody;
import com.rp.ecommercebackend.api.model.RegistrationBody;
import com.rp.ecommercebackend.exception.UserAlreadyExistsException;
import com.rp.ecommercebackend.model.LocalUser;
import com.rp.ecommercebackend.model.dao.LocalUserDAO;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
@Log4j2
public class UserService {

    private LocalUserDAO userDAO;
    private EncryptionService encryptionService;
    private JWTService jwtService;

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
        user.setPassword(encryptionService.encryptPassword(registrationBody.password()));
        return userDAO.save(user);
    }

    public String loginUser(LoginBody loginBody) {
        log.info(loginBody);
        Optional<LocalUser> optUser = userDAO.findByUsernameIgnoreCase(loginBody.username());
        if (optUser.isPresent()) {
            LocalUser user = optUser.get();
            log.info("-----" + user + " " + encryptionService.verifyPassword(loginBody.password(), user.getPassword()));
            if (encryptionService.verifyPassword(loginBody.password(), user.getPassword())) {
                return jwtService.generateJWT(user);
            }
        }
        return null;
    }
}
