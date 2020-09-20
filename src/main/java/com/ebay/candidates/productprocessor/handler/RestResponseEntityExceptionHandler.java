package com.ebay.candidates.productprocessor.handler;

import com.ebay.candidates.productprocessor.exception.ApiError;
import com.ebay.candidates.productprocessor.exception.ProductNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = { ProductNotFound.class})
  protected ResponseEntity<Object> handleNotFound(RuntimeException ex) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
    return new ResponseEntity<>(apiError, apiError.getHttpStatus());
  }
}
