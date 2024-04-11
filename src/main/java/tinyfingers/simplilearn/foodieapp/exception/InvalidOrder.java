package tinyfingers.simplilearn.foodieapp.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidOrder extends RuntimeException {
  private String message;
}
