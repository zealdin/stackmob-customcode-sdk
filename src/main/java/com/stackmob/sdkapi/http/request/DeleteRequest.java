package com.stackmob.sdkapi.http.request;

import java.util.List;
import java.util.Map;

public class DeleteRequest extends HttpRequestWithoutBody {
    DeleteRequest(String url, List<Map.Entry<String, String>> headers) {
        super(url, headers);
    }

    DeleteRequest(String url) {
        super(url, HttpRequest.EmptyHeaders);
    }
}
