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

package com.stackmob.sdkapi;

import com.stackmob.core.DatastoreException;
import com.stackmob.core.PushServiceException;

import java.util.List;
import java.util.Map;

/**
 * Provides access to the StackMob push notification service, allowing the creation of push notifications directly from
 * a custom code method.
 */
public interface PushService {

  /**
   * Send a push notification to all devices identified by the tokens provided. This is a shorthand for
   * <code>sendPush(tokens, badge, sound, alert, true)</code>
   *
   * @param tokens the device tokens to which the push notification should be sent
   * @param badge the badge for the push notification; specifies the number to be displayed with the app icon
   * @param sound the sound to be played by the devices
   * @param alert the text of the alert to be displayed by the devices
   * @throws PushServiceException if an error occurs
   */
  void sendPush(List<String> tokens, int badge, String sound, String alert) throws PushServiceException;

  /**
   * Send a push notification to all devices identified by the recipients list. The meaning of the entries in the
   * recipients list is controlled by the <code>recipientsAreTokens</code> parameter. If true, the recipients list
   * is considered to consist of device tokens. If false, the recipients list will be considered to be user IDs. In
   * either case, tokens need to be registered via the {@link #registerToken(String, String)} call, before they can be
   * used.
   *
   * @param recipients the device tokens to which the push notification should be sent
   * @param badge the badge for the push notification; specifies the number to be displayed with the app icon
   * @param sound the sound to be played by the devices
   * @param alert the text of the alert to be displayed by the devices
   * @param recipientsAreTokens the type of entries in the recipients list. True if specifying tokens; false for users.
   * @throws PushServiceException if an error occurs
   */
  void sendPush(List<String> recipients, int badge, String sound, String alert, boolean recipientsAreTokens)
    throws PushServiceException;

  /**
   * Broadcast a push notification to all devices running this app.
   *
   * @param badge the badge for the push notification; specifies the number to be displayed with the app icon
   * @param sound the sound to be played by the devices
   * @param alert the text of the alert to be displayed by the devices
   * @throws PushServiceException if an error occurs
   */
  void broadcastPush(int badge, String sound, String alert) throws PushServiceException;

  /**
   * Get the tokens, which have been reported as expired by Apple's feedback service
   *
   * @param clear if true, the set of expired tokens will be cleared after the call. if false, the current token set
   * will be retained on the server
   * @return A list of expired tokens, represented as a <code>Map<String, Long></code>, where the token is the key, and
   * the value is the expiration time, represented as milliseconds since January 1, 1970 UTC
   * @throws DatastoreException if an error occurs
   */
  Map<String, Long> getExpiredTokens(boolean clear) throws DatastoreException;

  /**
   * Assign a push token to the specified user. After a token is registered, it may be used to push messages to the
   * user. If the username is null, or an empty string, the token will be registered without a username.
   *
   * @param username a username to associate with the specified token
   * @param token    iOS device token
   * @throws DatastoreException if an error occurs
   */
  void registerToken(String username, String token) throws DatastoreException;

  /**
   * Finds the device push tokens for the given user list. Will not return the tokens, which were marked as expired by
   * the feedback service. Use {@link #getExpiredTokens(boolean)} to get the expired tokens.
   *
   * @param users list of user IDs
   * @return a map of user IDs to push tokens. If no token is registered for a user, then the user ID will not be
   * included in the returned map
   * @throws DatastoreException if an error occurs
   */
  Map<String, String> getTokensForUsers(List<String> users) throws DatastoreException;

  /**
   * Removes the token from the list of registered tokens.
   *
   * @param token token to be removed
   * @throws DatastoreException if an error occurs
   */
  void removeToken(String token) throws DatastoreException;

}
