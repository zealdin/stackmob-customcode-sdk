package com.stackmob.sdkapi.http.request;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

abstract class HttpRequestWithoutBody extends HttpRequest {
    HttpRequestWithoutBody(String url, List<Map.Entry<String, String>> headers) throws MalformedURLException {
        super(url, headers);
    }
}
