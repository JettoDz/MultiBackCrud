package com.jettodz.java_book_crud.util.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PermissionDeniedExceptions extends RuntimeException {

    private final String message = "Permission denied. You are not authorized to perform this action.";

}
