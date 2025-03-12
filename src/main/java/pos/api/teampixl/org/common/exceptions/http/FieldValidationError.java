package pos.api.teampixl.org.common.exceptions.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import pos.api.teampixl.org.common.exceptions.validation.InvalidFieldException;

@RestControllerAdvice
public class FieldValidationError {

    @ExceptionHandler(InvalidFieldException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String fieldValidationError(InvalidFieldException e) {
        return e.getMessage();
    }

}
