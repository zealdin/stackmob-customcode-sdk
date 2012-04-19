package com.stackmob.sdkapi.http.response;

import java.util.Map;
import java.util.List;

public class HttpResponse {
    private Integer code;
    private List<Map.Entry<String, String>> headers;
    private String body;

    /**
     * create a new HTTP response
     * @param code the response code
     * @param headers the response headers
     * @param body the response body
     */
    HttpResponse(Integer code, List<Map.Entry<String, String>> headers, String body) {
        this.code = code;
        this.headers = headers;
        if(body == null) {
            this.body = "";
        } else {
            this.body = body;
        }
    }

    /**
     * get the response code
     * @return the response code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * get the response headers
     * @return a list of the response headers. this list is not sorted
     */
    public List<Map.Entry<String, String>> getHeaders() {
        return headers;
    }

    /**
     * get the response body
     * @return the response body, or the empty string if there was none
     */
    public String getBody() {
        return body;
    }

    /**
     * convenience method for getBody().length > 0
     * @return true if it does have a body, false otherwise.
     */
    boolean hasBody() {
        return this.body.length() > 0;
    }
}