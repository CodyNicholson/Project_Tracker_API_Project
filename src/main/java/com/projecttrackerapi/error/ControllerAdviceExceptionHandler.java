package com.projecttrackerapi.error;

import com.projecttrackerapi.constants.Constants;
import com.projecttrackerapi.error.restCustomExceptions.BadRequestException;
import com.projecttrackerapi.error.restCustomExceptions.InternalServerErrorException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(value = { BadRequestException.class })
    protected ResponseEntity<ErrorResponseModel> handleBadRequestException(Exception ex) {
        logger.error(Constants.REST_BAD_REQUEST, ex);

        ErrorResponseModel errorDetails = new ErrorResponseModel(
                new Date(),
                Constants.REST_BAD_REQUEST,
                ex.getLocalizedMessage());

        return new ResponseEntity<ErrorResponseModel>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { InternalServerErrorException.class, RuntimeException.class })
    protected ResponseEntity<ErrorResponseModel> handleInternalServerError(Exception ex, WebRequest request) {

        logger.info(Constants.REST_INTERNAL_SERVER_ERROR, ex);

        ErrorResponseModel errorDetails = new ErrorResponseModel(
                new Date(),
                Constants.REST_INTERNAL_SERVER_ERROR,
                ex.getLocalizedMessage());

        return new ResponseEntity<ErrorResponseModel>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
