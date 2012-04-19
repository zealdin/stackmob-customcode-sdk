package com.stackmob.sdkapi.http.request;

import com.stackmob.sdkapi.http.Header;

import java.net.MalformedURLException;
import java.util.Set;

public class PutRequest extends HttpRequestWithBody {
    public PutRequest(String url, Set<Header> headers, String body) throws MalformedURLException {
        super(url, headers, body);
    }

    public PutRequest(String url, String body) throws MalformedURLException {
        super(url, EmptyHeaders, body);
    }
}
