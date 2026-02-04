package com.devtiro.tasks.dto;

public record ErrorResponse(
        int status,
        String message,
        String details
) {
}
