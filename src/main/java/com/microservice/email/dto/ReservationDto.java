package com.microservice.email.dto;

public record ReservationDto(String date, String startHour, String endHour, EmailRecipientDto emailRecipientDto, FacilityDto facilityDto) {
}
