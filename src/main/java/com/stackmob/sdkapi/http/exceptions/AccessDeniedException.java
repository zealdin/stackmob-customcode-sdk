package com.stackmob.sdkapi.http.exceptions;


public abstract class AccessDeniedException extends Exception {
    AccessDeniedException(String message) {
        super(message);
    }
}
