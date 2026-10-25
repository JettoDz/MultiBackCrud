package com.jettodz.crud.util.exceptions;

public class ErrorMessages {
    public static final String IDEMPOTENCY_KEY_CONFLICT = "Idempotency key conflict: Request is still in progress.";
    public static final String IDEMPOTENCY_KEY_MISMATCH = "Idempotency key mismatch: Request differs from the original.";
    public static final String IDEMPOTENCY_KEY_CORRUPTION = "Idempotency key corruption: The request does not match the original request.";
}
