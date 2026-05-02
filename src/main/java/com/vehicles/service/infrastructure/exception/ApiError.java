package com.vehicles.service.infrastructure.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        List<String> details
) {
    public ApiError(LocalDateTime timestamp, int status, String error, List<String> details) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.details = details != null ? List.copyOf(details) : List.of();
    }
}
