package com.rp.ecommercebackend.service;

import com.rp.ecommercebackend.api.model.LoginBody;
import com.rp.ecommercebackend.api.model.RegistrationBody;
import com.rp.ecommercebackend.exception.EmailFailureException;
import com.rp.ecommercebackend.exception.UserAlreadyExistsException;
import com.rp.ecommercebackend.exception.UserNotVerifiedException;
import com.rp.ecommercebackend.model.LocalUser;
import com.rp.ecommercebackend.model.VerificationToken;
import com.rp.ecommercebackend.model.dao.LocalUserDAO;
import com.rp.ecommercebackend.model.dao.VerificationTokenDAO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Log4j2
public class UserService {

    private LocalUserDAO userDAO;
    private EncryptionService encryptionService;
    private JWTService jwtService;
    private EmailService emailService;
    private VerificationTokenDAO verificationTokenDAO;

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException, EmailFailureException {
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
        VerificationToken verificationToken = new VerificationToken();
        emailService.sendVerificationEmail(verificationToken);
        verificationTokenDAO.save(verificationToken);
        return userDAO.save(user);
    }

    public String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException {
        log.info(loginBody);
        Optional<LocalUser> optUser = userDAO.findByUsernameIgnoreCase(loginBody.username());
        if (optUser.isPresent()) {
            LocalUser user = optUser.get();
            if (encryptionService.verifyPassword(loginBody.password(), user.getPassword())) {
                if (user.isEmailVerified()) {
                    return jwtService.generateJWT(user);
                } else {
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                    boolean resend = (verificationTokens.isEmpty()) ||
                            verificationTokens.get(0).getCreatedTimeStamp().before(new Timestamp(System.currentTimeMillis() - (1000 * 60 * 60)));
                    if (resend) {
                        VerificationToken verificationToken = createVerificationToken(user);
                        verificationTokenDAO.save(verificationToken);
                        emailService.sendVerificationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }
            }
        }
        return null;
    }

    private VerificationToken createVerificationToken(LocalUser user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setCreatedTimeStamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }

    @Transactional
    public boolean verifyUser(String token) {
        Optional<VerificationToken>  optToken= verificationTokenDAO.findByToken(token);
        if (optToken.isPresent()) {
            LocalUser user =  optToken.get().getUser();
            if (!user.isEmailVerified()) {
                user.setEmailVerified(true);
                userDAO.save(user);
                verificationTokenDAO.deleteByUser(user);
                return true;
            }
        }
        return false;
    }
}
