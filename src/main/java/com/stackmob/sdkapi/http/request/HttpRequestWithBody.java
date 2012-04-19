package com.stackmob.sdkapi.http.request;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

abstract class HttpRequestWithBody extends HttpRequest {
    private String body;
    HttpRequestWithBody(String url, List<Map.Entry<String, String>> headers, String body) throws MalformedURLException {
        super(url, headers);
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}