/**
 * Copyright 2011 StackMob
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stackmob.sdkapi.http.request;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class HttpRequest {
    protected static List<Map.Entry<String, String>> EmptyHeaders = new ArrayList<Map.Entry<String, String>>();

    private URL url;
    private List<Map.Entry<String, String>> headers;

    HttpRequest(String url, List<Map.Entry<String, String>> headers) throws MalformedURLException {
        this.url = new URL(url);
        this.headers = headers;
    }

    public URL getUrl() {
        return url;
    }

    public List<Map.Entry<String, String>> getHeaders() {
        return headers;
    }
}
