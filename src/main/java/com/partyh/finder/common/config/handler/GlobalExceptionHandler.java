package com.partyh.finder.common.config.handler;


import com.partyh.finder.common.exception.PFException;
import com.partyh.finder.common.exception.ResponseError;

import com.partyh.finder.common.utils.ExceptionsUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    @ResponseBody
    public ResponseEntity<ResponseError> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        ExceptionsUtil.logExceptions(log, e);
        return new ResponseEntity<>(
                new ResponseError(e, HttpStatus.valueOf(413), new HashMap<String, String>() {{
                    put("message", "File size exceeds the maximum allowed, 50 MB");
                }}),
                HttpStatus.valueOf(413));
    }


    @ExceptionHandler(value = PFException.class)
    @ResponseBody
    public ResponseEntity<ResponseError> handleValidationException(PFException e) {
        ExceptionsUtil.logExceptions(log, e);
        return new ResponseEntity<>(
                new ResponseError(e, HttpStatus.BAD_REQUEST, e.getDetails()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    @ResponseBody
    public ResponseEntity<ResponseError> handleNoSuchElementException(NoSuchElementException e) {
        ExceptionsUtil.logExceptions(log, e);
        return new ResponseEntity<>(
                new ResponseError(e, HttpStatus.NOT_FOUND),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseBody
    public ResponseEntity<ResponseError> handleBadRequestException(IllegalArgumentException e) {
        ExceptionsUtil.logExceptions(log, e);
        return new ResponseEntity<>(
                new ResponseError(e, HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseBody
    //method for exception to validation
    public ResponseEntity<ResponseError> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        ExceptionsUtil.logExceptions(log, e);
        return new ResponseEntity<>(
                new ResponseError(e, HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    //method for exception to validation
    public ResponseEntity<ResponseError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ExceptionsUtil.logExceptions(log, e);
        return new ResponseEntity<>(
                new ResponseError(e, HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ResponseEntity<ResponseError> handleNullPointerException(NullPointerException e) {
        ExceptionsUtil.logExceptions(log, e);
        return new ResponseEntity<>(
                new ResponseError(e, HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = MappingException.class)
    @ResponseBody
    public ResponseEntity<ResponseError> handleMappingException(MappingException e) {

        ExceptionsUtil.logExceptions(log, e);
        return new ResponseEntity<>(
                new ResponseError(e, HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //General Exception
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<ResponseError> handleException(Exception e) {
        ExceptionsUtil.logExceptions(log, e);
        return new ResponseEntity<>(
                new ResponseError(e, HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseError> handlerConstraintViolationException(ConstraintViolationException e) {
        Map<String, String> map = new HashMap<>();
        Set<ConstraintViolation<?>> handlerConstraintViolation = e.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : handlerConstraintViolation) {
            map.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }
        return new ResponseEntity<>(
                new ResponseError("Validation Error", HttpStatus.BAD_REQUEST, map),
                HttpStatus.BAD_REQUEST);

    }
}
