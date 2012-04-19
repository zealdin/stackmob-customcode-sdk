package com.stackmob.sdkapi.http.request;

import com.stackmob.sdkapi.http.Header;

import java.net.MalformedURLException;
import java.util.Set;

public class DeleteRequest extends HttpRequestWithoutBody {
    public DeleteRequest(String url, Set<Header> headers) throws MalformedURLException {
        super(url, headers);
    }

    public DeleteRequest(String url) throws MalformedURLException {
        super(url, HttpRequest.EmptyHeaders);
    }
}
