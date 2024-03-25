package com.microservice.email.dto;

public record CustomEmailDto(String email, String subject, String htmlContent) {
}
