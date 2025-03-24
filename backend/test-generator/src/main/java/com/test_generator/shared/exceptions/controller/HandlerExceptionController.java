package com.test_generator.shared.exceptions.controller;

import com.test_generator.shared.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class HandlerExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomException> handlerNotFoundException(NotFoundException exception){

        logException(exception);

        CustomException customException = new CustomException(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );

        return new ResponseEntity<>(
                customException,
                customException.getHttpStatus()
        );
    }

    @ExceptionHandler(FileSizeException.class)
    public ResponseEntity<CustomException> handlerNotFoundException(FileSizeException exception){

        logException(exception);

        CustomException customException = new CustomException(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );

        return new ResponseEntity<>(
                customException,
                customException.getHttpStatus()
        );
    }

    @ExceptionHandler(FileNameException.class)
    public ResponseEntity<CustomException> handlerNotFoundException(FileNameException exception){

        logException(exception);

        CustomException customException = new CustomException(
                HttpStatus.BAD_REQUEST,
                exception.getLocalizedMessage()
        );

        return new ResponseEntity<>(
                customException,
                customException.getHttpStatus()
        );
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<CustomException> handlerApiException(ApiException exception){

        logException(exception);

        CustomException customException = new CustomException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getLocalizedMessage()
        );

        return new ResponseEntity<>(
                customException,
                customException.getHttpStatus()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomException> handlerIllegalArgumentException(IllegalArgumentException exception){

        logException(exception);

        CustomException customException = new CustomException(
                HttpStatus.BAD_REQUEST,
                exception.getLocalizedMessage()
        );

        return new ResponseEntity<>(
                customException,
                customException.getHttpStatus()
        );
    }

    private void logException (Exception exception){

        log.error("Exception {} occurred at {} with message: {}",
                exception.getClass().getSimpleName(),
                exception.getStackTrace()[0],
                exception.getMessage());
    }
}
