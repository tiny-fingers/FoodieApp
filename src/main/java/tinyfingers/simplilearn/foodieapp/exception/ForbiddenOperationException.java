package tinyfingers.simplilearn.foodieapp.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ForbiddenOperationException extends RuntimeException {
  private String message;
}
