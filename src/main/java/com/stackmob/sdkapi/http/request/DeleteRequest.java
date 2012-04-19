package com.stackmob.sdkapi.http.request;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

public class DeleteRequest extends HttpRequestWithoutBody {
    DeleteRequest(String url, List<Map.Entry<String, String>> headers) throws MalformedURLException {
        super(url, headers);
    }

    DeleteRequest(String url) throws MalformedURLException {
        super(url, HttpRequest.EmptyHeaders);
    }
}
