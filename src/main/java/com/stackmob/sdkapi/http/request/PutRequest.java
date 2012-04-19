package com.stackmob.sdkapi.http.request;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

public class PutRequest extends HttpRequestWithBody {
    PutRequest(String url, List<Map.Entry<String, String>> headers, String body) throws MalformedURLException {
        super(url, headers, body);
    }

    PutRequest(String url, String body) throws MalformedURLException {
        super(url, EmptyHeaders, body);
    }
}
