package ru.practicum.exception;

public class AccessException extends RuntimeException {
    public AccessException(final String message) {
        super(message);
    }
}
