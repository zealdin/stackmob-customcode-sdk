package com.stackmob.sdkapi.caching.exceptions;

public class TTLTooBigException extends Exception {
    public TTLTooBigException(Long ttlMilliseconds) {
        super(String.format("the TTL of %d milliseconds is too big", ttlMilliseconds));
    }

}
