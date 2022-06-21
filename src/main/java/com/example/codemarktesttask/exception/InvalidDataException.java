package com.example.codemarktesttask.exception;

public class InvalidDataException extends AbstractException{
    public InvalidDataException(String message, String techInfo) {
        super(message, techInfo);
    }
}
