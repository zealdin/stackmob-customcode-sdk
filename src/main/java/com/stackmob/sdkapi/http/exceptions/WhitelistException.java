package com.stackmob.sdkapi.http.exceptions;


public class WhitelistException extends AccessDeniedException {
    WhitelistException(String domain) {
        super(String.format("the domain %s is not on the HTTP whitelist", (domain)));
    }
}
