/**
 * Copyright 2011-2012 StackMob
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

import com.stackmob.core.MethodVerb;

import java.util.Map;

/**
 * Represents an API request.
 */
public class ProcessedAPIRequest {
  
  private final MethodVerb verb;
  private final String url;
  private final String loggedInUser;
  private final Map<String, String> params;
  private final String appName;
  private final int apiVersion;
  private final String methodName;
  private final long counter;
  
  /**
   * Create a new processed API request.
   *
   * @param verb the HTTP verb specified
   * @param url the url which represents the request
   * @param apiVersion the api version number associated with this request
   * @param params the query parameters of the request
   * @param appName the name of the app this request refers to
   * @param methodName the name of the method being called by the request
   * @param loggedInUser if a logged in user sent the request, their username is represented here
   * @param counter the number of requests this particular JVM instance has handled up until this request
   */
  public ProcessedAPIRequest(MethodVerb verb, String url, String loggedInUser, Map<String, String> params,
                             String appName, int apiVersion, String methodName, long counter) {
    this.verb = verb;
    this.url = url;
    this.appName = appName;
    this.apiVersion = apiVersion;
    this.methodName = methodName;
    this.loggedInUser = (loggedInUser != null && loggedInUser.isEmpty()) ? null : loggedInUser;
    this.params = params;
    this.counter = counter;
  }

  /**
   * Returns the the HTTP verb.
   *
   * @return the HTTP verb associated with the request
   * @see <code>MethodVerb</code>
   */
  public MethodVerb getVerb() {
    return verb;
  }

  /**
   * Returns the URL representing the request.
   *
   * @return the URL representing this request
   */
  public String getUrl() {
    return url;
  }

  /**
   * Returns the logged in user.
   *
   * @return the loggedInUser, or <code>null</code> if the user performing the request has not logged in
   */
  public String getLoggedInUser() {
    return loggedInUser;
  }

  /**
   * The query parameters.
   *
   * @return the query parameters
   */
  public Map<String, String> getParams() {
    return params;
  }

  /**
   * Returns the method name.
   *
   * @return the method name
   */
  public String getMethodName() {
    return methodName;
  }

  @Override
  public String toString() {
    return "Request: " + getUrl();
  }

  /**
   * Returns the application name.
   *
   * @return the application name
   */
  @Deprecated
  public String getAppUrlSafeName() {
    return appName;
  }

  /**
   * Returns the application name.
   *
   * @return the application name
   */
  public String getAppName() {
    return appName;
  }

  /**
   * Returns the API version number.
   *
   * @return the API version number for the request
   */
  public int getApiVersion() {
    return apiVersion;
  }

  /**
   * Returns the request count.
   *
   * @return the total number of requests across all apps for a particular client during the life of the current JVM
   */
  public long getCounter() {
    return counter;
  }
  
}
