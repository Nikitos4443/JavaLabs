package org.example.SkiPass.exceptions;

public class CardIsExpiredException extends RuntimeException {
    public CardIsExpiredException(String message) {
        super(message);
    }
}
