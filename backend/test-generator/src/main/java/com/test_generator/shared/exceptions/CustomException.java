package com.test_generator.shared.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomException {

    private Timestamp timeStamp = new Timestamp(System.currentTimeMillis());

    private HttpStatus httpStatus;

    private String message;

    public CustomException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
