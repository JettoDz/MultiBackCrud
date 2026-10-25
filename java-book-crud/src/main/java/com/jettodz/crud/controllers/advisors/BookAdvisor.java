package com.jettodz.crud.controllers.advisors;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jettodz.crud.controllers.BookController;

@RestControllerAdvice(assignableTypes = BookController.class)
public class BookAdvisor extends BaseAdvisor {

}
