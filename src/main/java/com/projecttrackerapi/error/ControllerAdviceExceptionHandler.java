package com.projecttrackerapi.error;

import com.projecttrackerapi.constants.Constants;
import com.projecttrackerapi.error.restCustomExceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@Slf4j
public class ControllerAdviceExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.error(Constants.REST_BAD_REQUEST, ex);

        ErrorResponseModel errorDetails;
        if (ex.getLocalizedMessage().contains(Constants.CANNOT_DESERIALIZE_UUID_ERROR_MESSAGE)) {
            errorDetails = new ErrorResponseModel(
                    new Date(),
                    Constants.REST_BAD_REQUEST,
                    Constants.INVALID_PROJECT_ID_SENT_IN_BODY);
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
        log.error(Constants.REST_BAD_REQUEST, ex);

        ErrorResponseModel errorDetails = new ErrorResponseModel(
                new Date(),
                Constants.REST_BAD_REQUEST,
                ex.getMessage());

        return new ResponseEntity<ErrorResponseModel>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { NotFoundException.class })
    protected ResponseEntity<ErrorResponseModel> handleNotFoundException(Exception ex) {
        log.error(Constants.REST_NOT_FOUND, ex);

        ErrorResponseModel errorDetails = new ErrorResponseModel(
                new Date(),
                Constants.REST_NOT_FOUND,
                ex.getMessage());

        return new ResponseEntity<ErrorResponseModel>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { ConflictException.class })
    protected ResponseEntity<ErrorResponseModel> handleConflictException(Exception ex) {
        log.error(Constants.REST_CONFLICT, ex);

        ErrorResponseModel errorDetails = new ErrorResponseModel(
                new Date(),
                Constants.REST_CONFLICT,
                ex.getMessage());

        return new ResponseEntity<ErrorResponseModel>(errorDetails, HttpStatus.CONFLICT);
    }

    // IMPORTANT NOTE: This also covers invalid UUIDs sent as path parameters which
    //  causes an internal server error that I translate to a Bad Request
    @ExceptionHandler(value = { InternalServerErrorException.class, RuntimeException.class })
    protected ResponseEntity<ErrorResponseModel> handleInternalServerError(Exception ex) {
        log.info(Constants.REST_INTERNAL_SERVER_ERROR, ex);

        ErrorResponseModel errorDetails;
        if (ex.getLocalizedMessage().contains(Constants.INVALID_UUID_STRING)) {
            errorDetails = new ErrorResponseModel(
                    new Date(),
                    Constants.REST_BAD_REQUEST,
                    Constants.INVALID_ID_SENT_IN_PATH);
        } else {
            errorDetails = new ErrorResponseModel(
                    new Date(),
                    Constants.REST_INTERNAL_SERVER_ERROR,
                    ex.getMessage());
        }

        return new ResponseEntity<ErrorResponseModel>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { ServiceUnavailableException.class, Exception.class })
    protected ResponseEntity<ErrorResponseModel> handleServiceUnavailableError(Exception ex) {
        log.info(Constants.REST_SERVICE_UNAVAILABLE, ex);

        ErrorResponseModel errorDetails = new ErrorResponseModel(
                new Date(),
                Constants.REST_SERVICE_UNAVAILABLE,
                ex.getMessage());

        return new ResponseEntity<ErrorResponseModel>(errorDetails, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
