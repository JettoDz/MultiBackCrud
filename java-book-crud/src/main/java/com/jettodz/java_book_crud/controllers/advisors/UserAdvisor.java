package com.jettodz.java_book_crud.controllers.advisors;

import com.jettodz.java_book_crud.controllers.UserController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = UserController.class)
public class UserAdvisor extends BaseAdvisor {

}
