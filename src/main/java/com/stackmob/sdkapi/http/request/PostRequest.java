package com.stackmob.sdkapi.http.request;

import com.stackmob.sdkapi.http.Header;

import java.net.MalformedURLException;
import java.util.Set;

public class PostRequest extends HttpRequestWithBody {
    public PostRequest(String url, Set<Header> headers, String body) throws MalformedURLException {
        super(url, headers, body);
    }

    public PostRequest(String url, String body) throws MalformedURLException {
        super(url, EmptyHeaders, body);
    }
}
