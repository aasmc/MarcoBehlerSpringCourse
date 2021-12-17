package ru.aasmc.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @RestControllerAdvice makes sure that Spring applies whatever this class
 * contains to all of @Controllers or @RestControllers that it knows. It writes JSON/XML directly
 * to the @ResponseBody
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    /**
     * This method is invoked when MethodArgumentException is thrown when @RestController
     * fails to validate arguments annotated with @Valid
     * @param exception
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        // TODO you can choose to return your custom object here, which will
        //  then get transformed to json/xml etc.
        return "Sorry, that was not quite right: " + exception.getMessage();
    }

    /**
     * This method is invoked when ConstraintViolationException is thrown when @RestController
     * fails to validate arguments annotated with @RequestParam and additional validation
     * annotations.
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolation(ConstraintViolationException e) {
        // TODO you can choose to return your custom object here, which will
        //  then get transformed to json/xml etc.
        return "Sorry, that was not quite right: " + e.getMessage();
    }

}
