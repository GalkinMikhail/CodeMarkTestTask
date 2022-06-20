package com.example.codemarktesttask.exception;

public class AbstractException extends RuntimeException {

    private String techInfo;

    public AbstractException(String message, String techInfo) {
        super(message);
        setTechInfo(techInfo);
    }

    public String getTechInfo() {
        return techInfo;
    }

    public void setTechInfo(String techInfo) {
        this.techInfo = techInfo;
    }
}