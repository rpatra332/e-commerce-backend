package com.rp.ecommercebackend.api.model;

public record LoginResponse(String jwt, Boolean success, String failureMessage) {
}
