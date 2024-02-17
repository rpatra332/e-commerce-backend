package com.rp.ecommercebackend.api.controller.auth;

import com.rp.ecommercebackend.api.model.RegistrationBody;
import com.rp.ecommercebackend.exception.UserAlreadyExistsException;
import com.rp.ecommercebackend.model.LocalUser;
import com.rp.ecommercebackend.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private UserService userService;
    @GetMapping("/register")
    public ResponseEntity<LocalUser> registerUser(@Valid @RequestBody RegistrationBody registrationBody) {
        try {
            LocalUser newUser = userService.registerUser(registrationBody);
            return ResponseEntity.ok().body(newUser);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
