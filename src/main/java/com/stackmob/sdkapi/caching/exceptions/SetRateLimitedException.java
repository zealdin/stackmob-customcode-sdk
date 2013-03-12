package com.stackmob.sdkapi.caching.exceptions;

public class SetRateLimitedException extends RateLimitedException {
    public SetRateLimitedException(String key) {
        super(key);
    }
}
