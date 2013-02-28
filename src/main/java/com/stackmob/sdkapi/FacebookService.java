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

package com.stackmob.sdkapi;

import com.stackmob.core.FacebookServiceException;

/**
 * The Facebook service.
 */
public interface FacebookService {

  /**
   * Creates a new user object, using the given facebook token for authentication. The user will be assigned the
   * Facebook ID, containted in the access token, which may be used to authenticate the user in the future.
   *
   * @param modelName the name of the relevant object model; must be a type already declared for the current application
   * @param username a new unique username in your schema. A user object with this username will be created
   * @param accessToken the Facebook acess token
   * @return true if the operation was successful, false if a user could not be created
   * @throws FacebookServiceException if the username already exists, or the facebook user id is already assigned to
   * another user, or a datastore error occurs
   */
  public boolean createUserWithFacebookId(String modelName, String username, String accessToken)
    throws FacebookServiceException;

  /**
   * Assigns the Facebook user ID contained in the access token to the user with the given username. The access token
   * may be used to authenticate the user in the future.
   *
   * @param modelName the name of the relevant object model; must be a type already declared for the current application
   * @param username the user, which should be assigned the facebook credentials
   * @param accessToken the Facebook access token
   * @return ture if the operation was successful, false if a user with the given name does not exist
   * @throws FacebookServiceException if the Facebook user ID is already assigned to another user, or a datastore error
   * occurs
   */
  public boolean linkFacebookIdToUser(String modelName, String username, String accessToken)
    throws FacebookServiceException;

  /**
   * Disconnects the user with the given username from any linked Facebook access tokens. The access token will then
   * cease to authenticate this user. The user and token may be linked again in the future, if so desired.
   *
   * If the user with the given username does not exist, or is not linked to a token, calling this will still succeed.
   *
   * @param modelName the name of the relevant object model; must be a type already declared for the current application
   * @param username the user whose Facebook token should be removed
   * @throws FacebookServiceException if a datastore error occurs
   */
  public void unlinkFacebookIdFromUser(String modelName, String username)
    throws FacebookServiceException;

  /**
   * Find the username assigned to the Facebook user ID contained in the given access token.
   *
   * @param modelName the name of the relevant object model; must be a type already declared for the current application
   * @param accessToken the Facebook access token
   * @return The username corresponding to the Facebook credentials, or null, if the user is not found
   * @throws FacebookServiceException if a datastore error occurs
   */
  public String findUser(String modelName, String accessToken) throws FacebookServiceException;

  /**
   * Post a message to a user's Facebook wall.
   *
   * @param modelName the name of the relevant object model; must be a type already declared for the current application
   * @param username the user, on whose wall the message will be posted
   * @param messageText the message to post
   * @return id of the message, or null if the operation did not succeed
   * @throws FacebookServiceException if the user is not assinged a Facebook ID, or if an authentication error, or a
   * datastore error occurred
   */
  public String publishMessage(String modelName, String username, String messageText) throws FacebookServiceException;

}
