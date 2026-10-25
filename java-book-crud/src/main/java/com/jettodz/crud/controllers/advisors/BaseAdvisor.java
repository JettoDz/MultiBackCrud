package com.jettodz.crud.controllers.advisors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.jettodz.crud.util.exceptions.PermissionDeniedExceptions;

public abstract class BaseAdvisor {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(PermissionDeniedExceptions.class)
    public ResponseEntity<String> handlePermissionDeniedExceptions(PermissionDeniedExceptions e) {
        return ResponseEntity.status(403).body(e.getMessage());
    }

    /** Catches cases when the received argument type does not match the expected type */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest().body("Invalid argument type: " + ex.getMessage());
    }

}
