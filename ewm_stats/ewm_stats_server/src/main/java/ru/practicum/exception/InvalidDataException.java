package ru.practicum.exception;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(final String message) {
        super(message);
    }
}
