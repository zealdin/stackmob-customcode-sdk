/**
 * Copyright 2012 StackMob
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

package com.stackmob.core.rest;

import org.jboss.netty.handler.codec.http.HttpResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the response to an API request. When returned, the response map will be processed into a JSON string and
 * returned as a proper API response by StackMob.
 */
public class ResponseToProcess {

  private final HttpResponseStatus responseStatus;
  private final Map<String, ?> responseMap;

  /**
   * Creates a new API response to process.
   *
   * @param responseStatus the response status to return
   * @param responseMap the response map
   */
  @Deprecated
  public ResponseToProcess(HttpResponseStatus responseStatus, Map<String, ?> responseMap) {
    this.responseStatus = responseStatus;
    this.responseMap = responseMap;
  }

  /**
   * Creates a new API response to process.
   *
   * @param responseStatus the response status to return
   */
  @Deprecated
  public ResponseToProcess(HttpResponseStatus responseStatus) {
    this.responseStatus = responseStatus;
    this.responseMap = new HashMap<String, Object>();
  }

  /**
   * Creates a new API response to process.
   *
   * @param responseCode the HTTP response code
   * @param responseMap the response map
   */
  public ResponseToProcess(int responseCode, Map<String, ?> responseMap) {
    this.responseStatus = HttpResponseStatus.valueOf(responseCode);
    this.responseMap = responseMap;
  }

  /**
   * Creates a new API response to process.
   * 
   * @param responseCode the HTTP response code
   */
  public ResponseToProcess(int responseCode) {
    this.responseStatus = HttpResponseStatus.valueOf(responseCode);
    this.responseMap = new HashMap<String, Object>();
  }

  /**
   * Returns the HTTP response status.
   *
   * @return the response status
   */
  @Deprecated
  public HttpResponseStatus getResponseStatus() {
    return responseStatus;
  }

  /**
   * Returns the HTTP response code.
   *
   * @return the response code
   */
  public int getResponseCode() {
    return responseStatus.getCode();
  }

  /**
   * Returns the response map.
   *
   * @return the response map
   */
  public Map<String, ?> getResponseMap() {
    return responseMap;
  }

}
