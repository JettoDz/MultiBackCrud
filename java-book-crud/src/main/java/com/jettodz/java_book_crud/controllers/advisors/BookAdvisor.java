package com.jettodz.java_book_crud.controllers.advisors;

import com.jettodz.java_book_crud.controllers.BookController;
import com.jettodz.java_book_crud.util.exceptions.PermissionDeniedExceptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = BookController.class)
public class BookAdvisor extends BaseAdvisor {

}
