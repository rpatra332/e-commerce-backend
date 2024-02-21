package com.rp.ecommercebackend.api.controller.auth;

import com.rp.ecommercebackend.api.model.LoginBody;
import com.rp.ecommercebackend.api.model.LoginResponse;
import com.rp.ecommercebackend.api.model.RegistrationBody;
import com.rp.ecommercebackend.exception.EmailFailureException;
import com.rp.ecommercebackend.exception.UserAlreadyExistsException;
import com.rp.ecommercebackend.exception.UserNotVerifiedException;
import com.rp.ecommercebackend.model.LocalUser;
import com.rp.ecommercebackend.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.ok(newUser);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody) {
        String jwt;
        try {
            jwt = userService.loginUser(loginBody);
        } catch (UserNotVerifiedException e) {
            String reason = "USER_NOT_VERIFIED";
            if (e.isNewEmailSent()) {
                reason += "_EMAIL_RESENT";
            }
            LoginResponse response = new LoginResponse(null, false, reason);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LoginResponse loginResponse = new LoginResponse(jwt,true, null);
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/me")
    public LocalUser getLoggedInUserProfile(@AuthenticationPrincipal LocalUser user) {
        log.info(user);
        return user;
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verifyEmail(@RequestParam String token) {
        if (userService.verifyUser(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
