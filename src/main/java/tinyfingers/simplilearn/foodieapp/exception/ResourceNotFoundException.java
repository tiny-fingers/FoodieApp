package tinyfingers.simplilearn.foodieapp.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    private String message;
}
