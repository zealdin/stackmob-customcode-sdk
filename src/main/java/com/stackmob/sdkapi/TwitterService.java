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

import com.stackmob.core.TwitterServiceException;

/**
 * The Twitter service.
 */
public interface TwitterService {

  /**
   * Creates a user with the given StackMob username and Twitter token and token secret.
   *
   * @param modelName the name of the relevant object model; must be a type already declared for the current application
   * @param smUsername the StackMob username
   * @param token the Twitter token
   * @param tokenSecret the Twitter token secret
   * @return true if successful; false otherwise
   * @throws TwitterServiceException if an exception has occurred in the Twitter service
   */
  boolean createUserWithTwitter(String modelName, String smUsername, String token, String tokenSecret)
    throws TwitterServiceException;

  /**
   * Links an existing user with the Twitter user identified by the given token and
   * token secret.
   *
   * @param modelName the name of the relevant object model; must be a type already declared for the current application
   * @param smUsername the StackMob username
   * @param token the Twitter token
   * @param tokenSecret the Twitter token secret
   * @return true if successful; false otherwise
   * @throws TwitterServiceException if an exception has occurred in the Twitter service
   */
  boolean linkUserWithTwitter(String modelName, String smUsername, String token, String tokenSecret)
    throws TwitterServiceException;

  /**
   * Finds a StackMob user with the given Twitter token and token secret and then
   * verifies the user's credentials are valid.
   *
   * @param modelName the name of the relevant object model; must be a type already declared for the current application
   * @param token the Twitter Token
   * @param tokenSecret the Twitter token secret
   * @return the StackMob username
   * @throws TwitterServiceException if an exception has occurred in the Twitter service
   */
  String findAndVerifyUser(String modelName, String token, String tokenSecret) throws TwitterServiceException;

  /**
   * Finds a StackMob user with the given Twitter user ID and then verifies the
   * user's credentials are valid.
   *
   * @param modelName the name of the relevant object model; must be a type already declared for the current application
   * @param twUserId the Twitter user ID
   * @return the StackMob username
   * @throws TwitterServiceException if an exception has occurred in the Twitter service
   */
  String findAndVerifyUser(String modelName, String twUserId) throws TwitterServiceException;

  /**
   * Finds a StackMob username given the associated Twitter user ID.
   *
   * @param modelName the name of the relevant object model; must be a type already declared for the current application
   * @param twUserId the Twitter user ID
   * @return the StackMob username
   * @throws TwitterServiceException if an exception has occurred in the Twitter service
   */
  String findUsername(String modelName, String twUserId) throws TwitterServiceException;

  /**
   * Updates a user's Twitter status with the given message.
   *
   * @param modelName the name of the relevant object model; must be a type already declared for the current application
   * @param smUsername the StackMob username
   * @param statusMsg the status message
   * @return true if successful; false otherwise
   * @throws TwitterServiceException if an exception has occurred in the Twitter service
   */
  boolean updateStatus(String modelName, String smUsername, String statusMsg) throws TwitterServiceException;

  /**
   * Verifies a user's Twitter credentials.
   *
   * @param modelName the name of the relevant object model; must be a type already declared for the current application
   * @param smUsername the StackMob username
   * @return true if successful; false otherwise
   * @throws TwitterServiceException if an exception has occurred in the Twitter service
   */
  boolean verifyCredentials(String modelName, String smUsername) throws TwitterServiceException;

}
