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

package com.stackmob.core.jar;

import com.stackmob.core.customcode.CustomCodeMethod;

import java.util.List;

/**
 * One class in the JAR must implement this interface, and that same class should be marked as the entry point in the
 * manifest for the custom code JAR file.
 *
 * Note: Classes extending <code>JarEntryObject</code> must use a parameterless no-arg constructor.
 */
public abstract class JarEntryObject {
  
  /**
   * Get all custom code methods the API may use for responding to API requests. These <code>CustomCodeMethod</code>
   * objects should be properly instantiated as the instances returned by this method will be used to respond to API
   * requests. Classes extending <code>CustomCodeMethod</code>, but not instantiated and returned by
   * this method, will not be available via the API.
   * 
   * @return the list of custom code methods available to the API
   */
  public abstract List<CustomCodeMethod> methods();
  
}
