package tinyfingers.simplilearn.foodieapp.global;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tinyfingers.simplilearn.foodieapp.exception.ForbiddenOperationException;
import tinyfingers.simplilearn.foodieapp.exception.InvalidOrder;
import tinyfingers.simplilearn.foodieapp.exception.InvalidUserException;
import tinyfingers.simplilearn.foodieapp.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler({InvalidOrder.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleInvalidOrderException(InvalidOrder ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ForbiddenOperationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public String handleForbiddenOperationException(ForbiddenOperationException ex) {
        return ex.getMessage();
    }
    @ExceptionHandler(InvalidUserException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public String handleInvalidUserException(InvalidUserException ex) {
        return ex.getMessage();
    }
}
