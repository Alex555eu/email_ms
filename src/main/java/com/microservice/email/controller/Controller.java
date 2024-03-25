package com.microservice.email.controller;

import com.microservice.email.dto.CustomEmailDto;
import com.microservice.email.dto.EmailRecipientDto;
import com.microservice.email.dto.ReservationDto;
import com.microservice.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Slf4j
@ResponseBody
@RestController
@RequestMapping("/email-api")
public class Controller {

    private final EmailService emailService;
    private final TemplateEngine templateEngine;

    public Controller(EmailService emailService, TemplateEngine templateEngine) {
        this.emailService = emailService;
        this.templateEngine = templateEngine;
    }

    @PostMapping("/new-account")
    public ResponseEntity<String> index(@RequestBody EmailRecipientDto emailRecipientDto) {
        String htmlTemplate = "new_account.html";
        Context context = new Context();
        context.setVariable("emailRecipientDto", emailRecipientDto);
        String htmlContent = templateEngine.process(htmlTemplate, context);

        return emailService.sendHtmlMessage(emailRecipientDto.email(), "Welcome", htmlContent);
    }

    @PostMapping("/new-reservation")
    public ResponseEntity<String> newReservation(@RequestBody ReservationDto reservationDto) {
        String htmlTemplate = "new_reservation.html";
        Context context = new Context();
        context.setVariable("reservationDto", reservationDto);
        String htmlContent = templateEngine.process(htmlTemplate, context);

        return emailService.sendHtmlMessage(reservationDto.emailRecipientDto().email(), "New Reservation", htmlContent);
    }

    @PostMapping("/update-reservation")
    public ResponseEntity<String> updateReservation(@RequestBody ReservationDto reservationDto) {
        String htmlTemplate = "update_reservation.html";
        Context context = new Context();
        context.setVariable("reservationDto", reservationDto);
        String htmlContent = templateEngine.process(htmlTemplate, context);

        return emailService.sendHtmlMessage(reservationDto.emailRecipientDto().email(), "We Updated Your Reservation", htmlContent);
    }

    @PostMapping("/delete-reservation")
    public ResponseEntity<String> deleteReservation(@RequestBody EmailRecipientDto emailRecipientDto) {
        String htmlTemplate = "delete_reservation.html";
        Context context = new Context();
        context.setVariable("emailRecipientDto", emailRecipientDto);
        String htmlContent = templateEngine.process(htmlTemplate, context);

        return emailService.sendHtmlMessage(emailRecipientDto.email(), "Reservation Deleted", htmlContent);
    }

    @PostMapping("/custom")
    public ResponseEntity<String> customEmail(@RequestBody CustomEmailDto customEmailDto) {
        return emailService.sendHtmlMessage(customEmailDto.email(), customEmailDto.subject(), customEmailDto.htmlContent());

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred.");
    }


}
