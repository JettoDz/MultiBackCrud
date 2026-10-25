package com.jettodz.crud.controllers.advisors;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jettodz.crud.controllers.UserController;

@RestControllerAdvice(assignableTypes = UserController.class)
public class UserAdvisor extends BaseAdvisor {

}
