package ru.practicum.exception;

public class InvalidDataArgsException extends RuntimeException {
    public InvalidDataArgsException(final String message) {
        super(message);
    }
}
