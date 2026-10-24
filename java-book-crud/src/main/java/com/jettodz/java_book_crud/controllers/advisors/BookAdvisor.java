package com.jettodz.java_book_crud.controllers.advisors;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jettodz.java_book_crud.controllers.BookController;

@RestControllerAdvice(assignableTypes = BookController.class)
public class BookAdvisor extends BaseAdvisor {

}
