package com.example.TaxyBookingAndBilling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomGlobalExceptionHandler {
    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseBody
    public ResponseEntity<Object> userExists(EntityAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidLoginException.class)
    @ResponseBody
    public ResponseEntity<Object> invalidLogin(InvalidLoginException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseBody
    public ResponseEntity<Object> insufficientBalance(InsufficientBalanceException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BookingNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Object> bookingNotFound(BookingNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TaxiNotAvailableException.class)
    @ResponseBody
    public ResponseEntity<Object> taxiNotAvailable(TaxiNotAvailableException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(
                        (error) -> {
                            String fieldName = ((FieldError) error).getField();
                            String errorMessage = error.getDefaultMessage();
                            errors.append(fieldName).append(": ").append(errorMessage).append("\n");
                        });
        return ResponseEntity.badRequest().body(errors.toString());
    }
}