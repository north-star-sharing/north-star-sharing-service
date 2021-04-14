package edu.cnm.deepdive.northstarsharing.controller;

import edu.cnm.deepdive.northstarsharing.model.entity.Image;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles HTTP error responses for REST operations of the web service.
 */
@RestControllerAdvice
public class ExceptionAdvice {

  /**
   * Response for 404 "Not found" error.
   */
  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Image(s) not found")
  public void resourceNotFound() {
    // TODO Construct a meaningful error object, Office hours to see how to do this
  }
}
