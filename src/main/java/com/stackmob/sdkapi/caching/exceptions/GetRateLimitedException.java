package com.stackmob.sdkapi.caching.exceptions;

public class GetRateLimitedException extends RateLimitedException{
    public GetRateLimitedException(String key) {
        super(key);
    }
}
