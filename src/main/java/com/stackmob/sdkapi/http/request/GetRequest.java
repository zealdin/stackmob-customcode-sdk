package com.stackmob.sdkapi.http.request;

import com.stackmob.sdkapi.http.Header;

import java.net.MalformedURLException;
import java.util.Set;

public class GetRequest extends HttpRequestWithoutBody {
    public GetRequest(String url, Set<Header> headers) throws MalformedURLException {
        super(url, headers);
    }
    public GetRequest(String url) throws MalformedURLException {
        super(url, HttpRequest.EmptyHeaders);
    }
}
