package com.stackmob.sdkapi.http.request;
import com.stackmob.sdkapi.http.Header;

import java.net.MalformedURLException;
import java.util.Set;

abstract class HttpRequestWithBody extends HttpRequest {
    private String body;

    public HttpRequestWithBody(String url, Set<Header> headers, String body) throws MalformedURLException {
        super(url, headers);
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}