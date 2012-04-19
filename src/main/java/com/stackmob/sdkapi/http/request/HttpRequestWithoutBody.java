package com.stackmob.sdkapi.http.request;

import com.stackmob.sdkapi.http.Header;

import java.net.MalformedURLException;
import java.util.Set;

abstract class HttpRequestWithoutBody extends HttpRequest {
    public HttpRequestWithoutBody(String url, Set<Header> headers) throws MalformedURLException {
        super(url, headers);
    }
}
