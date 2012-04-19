package com.stackmob.sdkapi.http.exceptions;

class RateLimitedException extends AccessDeniedException {
    RateLimitedException() {
        super("this HTTP request has been rate limited");
    }
}
