package com.stackmob.sdkapi.http.request;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

public class PostRequest extends HttpRequestWithBody {
    PostRequest(String url, List<Map.Entry<String, String>> headers, String body) throws MalformedURLException {
        super(url, headers, body);
    }

    PostRequest(String url, String body) throws MalformedURLException {
        super(url, EmptyHeaders, body);
    }
}
