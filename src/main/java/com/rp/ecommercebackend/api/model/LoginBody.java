package com.rp.ecommercebackend.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginBody(
        @NotNull
        @NotBlank
        @Size(max = 255, min = 3)
        String username,
        @NotNull
        @NotBlank
        @Size(max = 32, min = 3)
        String password
) {}
