package org.example.anagram.exception;

public class StringValidationException extends RuntimeException {

  public StringValidationException(String message) {
    super(message);
  }
}
