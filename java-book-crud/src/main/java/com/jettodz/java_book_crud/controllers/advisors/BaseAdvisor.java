package com.jettodz.java_book_crud.controllers.advisors;

import com.jettodz.java_book_crud.util.exceptions.PermissionDeniedExceptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

}
