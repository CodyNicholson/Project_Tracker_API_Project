package com.projecttrackerapi.error;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.projecttrackerapi.constants.Constants;
import com.projecttrackerapi.error.restCustomExceptions.*;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ControllerAdviceExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger;

    public ControllerAdviceExceptionHandler(Logger logger) {
        this.logger = logger;
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        logger.error(Constants.REST_BAD_REQUEST, ex);

        ErrorResponseModel errorDetails;
        if (ex.getLocalizedMessage().contains(Constants.CANNOT_DESERIALIZE_UUID_ERROR_MESSAGE)) {
            errorDetails = new ErrorResponseModel(
                    new Date(),
                    Constants.REST_BAD_REQUEST,
                    Constants.INVALID_PROJECT_ID_SENT);
        } else {
            errorDetails = new ErrorResponseModel(
                    new Date(),
                    Constants.REST_BAD_REQUEST,
                    Constants.INVALID_JSON);
        }

        return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { BadRequestException.class })
    protected ResponseEntity<ErrorResponseModel> handleBadRequestException(Exception ex) {
        logger.error(Constants.REST_BAD_REQUEST, ex);

        ErrorResponseModel errorDetails = new ErrorResponseModel(
                new Date(),
                Constants.REST_BAD_REQUEST,
                ex.getMessage());

        return new ResponseEntity<ErrorResponseModel>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { NotFoundException.class })
    protected ResponseEntity<ErrorResponseModel> handleNotFoundException(Exception ex) {
        logger.error(Constants.REST_NOT_FOUND, ex);

        ErrorResponseModel errorDetails = new ErrorResponseModel(
                new Date(),
                Constants.REST_NOT_FOUND,
                ex.getMessage());

        return new ResponseEntity<ErrorResponseModel>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { ConflictException.class })
    protected ResponseEntity<ErrorResponseModel> handleConflictException(Exception ex) {
        logger.error(Constants.REST_CONFLICT, ex);

        ErrorResponseModel errorDetails = new ErrorResponseModel(
                new Date(),
                Constants.REST_CONFLICT,
                ex.getMessage());

        return new ResponseEntity<ErrorResponseModel>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = { InternalServerErrorException.class, RuntimeException.class })
    protected ResponseEntity<ErrorResponseModel> handleInternalServerError(Exception ex) {
        logger.info(Constants.REST_INTERNAL_SERVER_ERROR, ex);

        ErrorResponseModel errorDetails = new ErrorResponseModel(
                new Date(),
                Constants.REST_INTERNAL_SERVER_ERROR,
                ex.getMessage());

        return new ResponseEntity<ErrorResponseModel>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { ServiceUnavailableException.class, Exception.class })
    protected ResponseEntity<ErrorResponseModel> handleServiceUnavailableError(Exception ex) {
        logger.info(Constants.REST_SERVICE_UNAVAILABLE, ex);

        ErrorResponseModel errorDetails = new ErrorResponseModel(
                new Date(),
                Constants.REST_SERVICE_UNAVAILABLE,
                ex.getMessage());

        return new ResponseEntity<ErrorResponseModel>(errorDetails, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
