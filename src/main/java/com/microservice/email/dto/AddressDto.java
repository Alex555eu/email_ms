package com.microservice.email.dto;

public record AddressDto(String street, String buildingNo, String postalCode, String city, String state) {

    @Override
    public String toString() {
        return String.format("%s %s, %s %s, %s", street, buildingNo, postalCode, city, state);
    }
}
