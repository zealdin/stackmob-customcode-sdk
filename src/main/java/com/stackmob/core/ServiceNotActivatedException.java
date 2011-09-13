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

package com.stackmob.core;

/**
 * Thrown whenever a service has not been activated on the StackMob platform prior to trying to access it from custom
 * code.
 */
public class ServiceNotActivatedException extends Exception {

  private static final long serialVersionUID = 6335053017163885914L;

  /**
   * Constructs a new service not activated exception with the specified detail message.
   *
   * @param message the message
   */
  public ServiceNotActivatedException(String message) {
    super(message);
  }

  /**
   * Constructs a new service not activated exception with the specified detail message and cause.
   *
   * @param message the message
   * @param cause the cause
   */
  public ServiceNotActivatedException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new service not activated exception with the specified cause.
   *
   * @param cause the cause
   */
  public ServiceNotActivatedException(Throwable cause) {
    super(cause);
  }
  
}
