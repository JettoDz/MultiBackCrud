package com.jettodz.java_book_crud.controllers;

import com.jettodz.java_book_crud.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<String> createUser() {
        return ResponseEntity.ok("User created");
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUser() {
        return ResponseEntity.ok("User updated");
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser() {
        return ResponseEntity.ok("User deleted");
    }

}
