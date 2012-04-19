package com.stackmob.sdkapi.http.exceptions;

class RateLimitedException extends AccessDeniedException {
    public RateLimitedException() {
        super("this HTTP request has been rate limited");
    }
}
