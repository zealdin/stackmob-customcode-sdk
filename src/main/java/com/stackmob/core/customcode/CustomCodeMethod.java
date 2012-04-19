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

package com.stackmob.core.customcode;

import com.stackmob.core.rest.ProcessedAPIRequest;
import com.stackmob.core.rest.ResponseToProcess;
import com.stackmob.sdkapi.SDKServiceProvider;

import java.util.List;

/**
 * Defines an interface for a custom method within custom code. An object implementing <code>CustomCodeMethod</code>
 * will provide the functionality necessary to properly respond to API calls.
 */
public interface CustomCodeMethod {

  /**
   * Gets the name of the custom method. This is required to correctly route API calls to this
   * <code>CustomCodeMethod</code>.
   *
   * @return the name to which the custom method responds in the API
   */
  String getMethodName();

  /**
   * Gets the list of query parameters for this custom method. This is required if the <code>CustomCodeMethod</code>
   * uses query parameters.
   *
   * @return the list of valid query parameters used by this custom method
   */
  List<String> getParams();
  
  /**
   * Generates a response for the given API request.
   *
   * @param request represents the request sent to the API
   * @param serviceProvider the service provider that exposes access to StackMob services
   * @return the response containing a proper HTTP response code and JSON content
   */
  ResponseToProcess execute(ProcessedAPIRequest request, SDKServiceProvider serviceProvider);
  
}
