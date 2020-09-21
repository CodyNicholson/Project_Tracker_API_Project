package com.projecttrackerapi.error.restCustomExceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class InternalServerErrorException extends RuntimeException {
    private final String errorMessage;
    private final Throwable cause;
}
