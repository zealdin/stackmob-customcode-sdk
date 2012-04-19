package com.stackmob.sdkapi.http.exceptions;


public abstract class AccessDeniedException extends Exception {
    public AccessDeniedException(String message) {
        super(message);
    }
}
