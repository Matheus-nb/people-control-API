package com.api.peoplecontrol.domain.error;

import com.api.peoplecontrol.domain.models.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CpfAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> cpfAlreadyExistsException(CpfAlreadyExistsException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.CONFLICT, exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(message);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> emailAlreadyExistsException(EmailAlreadyExistsException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.CONFLICT, exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(message);
    }

    @ExceptionHandler(UuidNotFoundException.class)
    public ResponseEntity<ErrorMessage> uuidNotFoundException(UuidNotFoundException exception, WebRequest request) {
                ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    }

    @ExceptionHandler(DtoIsEmptyException.class)
    public ResponseEntity<ErrorMessage> dtoIsEmpty(DtoIsEmptyException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    }
}