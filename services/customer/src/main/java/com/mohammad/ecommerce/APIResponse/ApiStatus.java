package com.mohammad.ecommerce.APIResponse;

public enum ApiStatus {
    CUSTOMER_NOT_FOUND(404, "Customer not found."),
    INVALID_REQUEST(400, "The request is invalid."),
    INTERNAL_SERVER_ERROR(500, "An unexpected error occurred.");

    private final int statusCode;
    private final String message;

    ApiStatus(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
