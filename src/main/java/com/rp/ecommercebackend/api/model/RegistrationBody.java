package com.rp.ecommercebackend.api.model;

import jakarta.validation.constraints.*;

public record RegistrationBody(
        @NotNull
        @NotBlank
        @Size(max = 255, min = 3)
        String username,
        @Email
        @NotBlank
        @NotNull
        @Size(max = 320, min = 5)
        String email,
        @NotNull
        @NotBlank
        @Size(max = 32, min = 6)
        /*
         * Pattern for password:
         * - at least one alphabet.
         * - at least one digit.
         * - at least of length 6.
         * - at most of length 255.
         * - special symbols allowed.
         */
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[A-Za-z])(?=.*[a-zA-Z]).{6,}$")
        String password,
        @NotNull
        @NotBlank
        String firstName,
        @NotNull
        @NotBlank
        String lastName) {
}
