package ru.l3xu5.notes.components;

public class NotEnoughDateArgumentException extends RuntimeException {
    public NotEnoughDateArgumentException(String message) {
        super(message);
    }
}
