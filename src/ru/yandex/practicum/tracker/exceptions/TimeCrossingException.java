package ru.yandex.practicum.tracker.exceptions;

public class TimeCrossingException extends RuntimeException {
    public TimeCrossingException(String message) {
        super(message);
    }
}
