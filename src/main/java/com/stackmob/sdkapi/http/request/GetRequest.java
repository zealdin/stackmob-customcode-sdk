package com.stackmob.sdkapi.http.request;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

public class GetRequest extends HttpRequestWithoutBody {
    GetRequest(String url, List<Map.Entry<String, String>> headers) throws MalformedURLException {
        super(url, headers);
    }
    GetRequest(String url) throws MalformedURLException {
        super(url, HttpRequest.EmptyHeaders);
    }
}
