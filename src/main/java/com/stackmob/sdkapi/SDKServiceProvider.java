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

package com.stackmob.sdkapi;

import com.stackmob.core.ServiceNotActivatedException;

/**
 * Provides access to the various StackMob services.
 */
public interface SDKServiceProvider {

  /**
   * Get a <code>LoggerService</code> named corresponding to the given class. Use this for logging within custom code.
   *
   * @param clazz the logger service will be named after clazz
   * @return the logger service
   */
  LoggerService getLoggerService(Class clazz);

  /**
   * Get a <code>LoggerService</code> named using the given name. Use this for logging within custom code.
   *
   * @param name the name of the logger service
   * @return the logger service
   */
  LoggerService getLoggerService(String name);

  /**
   * Get the <code>DatastoreService</code> required to access the datastore for the current application.
   *
   * @return the datastore service
   */
  @Deprecated
  DatastoreService getDatastoreService();

  /**
   * Get the <code>DataService</code> required to access the datastore for the current application.
   *
   * @return the data service
   */
  DataService getDataService();

  /**
   * Get the <code>PushService</code> required to send push notifications for the current application.
   *
   * @return the push service
   * @throws ServiceNotActivatedException if the push service has not yet been set up for the current application
   */
  PushService getPushService() throws ServiceNotActivatedException;

  /**
   * Get the <code>TwitterService</code> required to communicate with Twitter for the current application.
   *
   * @return the Twitter service
   * @throws ServiceNotActivatedException if the Twitter service has not yet been set up for the current application
   */
  TwitterService getTwitterService() throws ServiceNotActivatedException;

  /**
   * Get the <code>FacebookService</code> required to communicate with Facebook for the current application.
   *
   * @return the Facebook service
   * @throws ServiceNotActivatedException if the Facebook service has not yet been set up for the current application
   */
  FacebookService getFacebookService() throws ServiceNotActivatedException;

  /**
   * Determine whether this custom code instance is currently running in sandbox.
   *
   * @return true if currently running in sandbox, false otherwise
   */
  boolean isSandbox();

  /**
   * StackMob internally assigns a globally unique version string to every custom code JAR. This method allows you to
   * read that version string.
   *
   * @return the current version of this custom code JAR
   */
  String getVersion();

}
