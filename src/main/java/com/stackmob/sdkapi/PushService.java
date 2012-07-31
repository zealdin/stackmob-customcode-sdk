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

import com.stackmob.core.DatastoreException;
import com.stackmob.core.PushServiceException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The push notification service.
 */
public interface PushService {

  public static enum TokenType {
      iOS,
      Android,
      AndroidGCM
    }

  public static class TokenAndType {
    private String token;
    private TokenType type;
    public TokenAndType(String token, TokenType type) {
      this.token = token;
      this.type = type;
    }

    public String getToken() { return this.token; }
    public TokenType getType() { return this.type; }
  }

  /**
   * Send a push notification to each device identified by the tokens provided.
   * Note that the total JSON encoded size (ie: the payload) of the pairs parameter must be no larger than 256 bytes
   * to push to iOS devices, and 1024 bytes to push to Android devices.
   *
   * <h2>Payload Sizes</h2>
   * As it supports multiple devices as its push destination, this method takes a best effort approach in sending push
   * notifications. Below is a table outlining to which devices the push notification will be sent for various payload
   * size ranges.
   *
   * <table border="1" cellpadding="2">
   *   <tr>
   *     <th>Payload size range (bytes)</th>
   *     <th>Behavior</th>
   *   </tr>
   *   <tr>
   *     <td>[0, 256]</td>
   *     <td>Sends to both Android and iOS devices</td>
   *   </tr>
   *   <tr>
   *     <td>(256, 1024]</td>
   *     <td>Sends to Android only, logs the iOS tokens to which the send would fail, and throws</td>
   *   </tr>
   *   <tr>
   *     <td>(1024,infinity)</td>
   *     <td>Sends to no devices, logs the failed tokens, and throws</td>
   *   </tr>
   * </table>
   *
   * This table should only be used as a guide. Use {@link #getSendableDevicesForPayload(java.util.Map)} in your code
   * to check which devices can be sent to given a payload.
   *
   * <h2>The pairs parameter</h2>
   * The following table outlines how this method handles the data in the pairs parameter for each device.
   * <table border="1" cellpadding="2">
   *   <tr>
   *     <th>Device</th>
   *     <th>Behavior</th>
   *   </tr>
   *   <tr>
   *     <td>iOS</td>
   *     <td>
   *       Any value whose key is "badge", "sound" or "alert" will be automatically put into the reserved "aps" dictionary.
   *       Per the iOS push notification documentation:
   *       <ul>
   *         <li>"badge" specifies the number to be displayed with the app icon </li>
   *         <li>"sound" specifies the sound to be played by the device</li>
   *         <li>"alert" specifies the text to be displayed by the device</li>
   *       </ul>
   *
   *       All other key/value pairs will be sent to the client in a custom dictionary called "smob".
   *     </td>
   *   </tr>
   *   <tr>
   *     <td>Android</td>
   *     <td>
   *       any value whose key is "collapse_key" will be used as the collapse key for the push notification.
   *       if no collapse key is given, "smob" will be used as the collapse key.
   *       if a collapse key is given, it will be removed from the payload prior to sending.
   *     </td>
   *   </tr>
   * </table>
   *
   * @see #getSendableDevicesForPayload(java.util.Map)
   *
   * @param tokens the tokens to send to
   * @param pairs the key/value pairs to send
   * @throws PushServiceException if an error occurred
   */
  void sendPushToTokens(List<TokenAndType> tokens, Map<String, String> pairs) throws PushServiceException;

  /**
   * send a push notification to all of the devices registered to the given users.
   * @param users the users to which to send
   * @param pairs the payload to send. This function treats this parameter exactly as does
   * {@link #sendPushToTokens(java.util.List, java.util.Map)}
   *
   * @throws PushServiceException if an error occurred
   *
   * @see #sendPushToTokens(java.util.List, java.util.Map)
   */
  void sendPushToUsers(List<String> users, Map<String, String> pairs) throws PushServiceException;

  /**
   * broadcast a push to all devices registered to the current application.
   *
   * @param pairs the payload to send. This function treats this parameter exactly as does
   * {@link #sendPushToTokens(java.util.List, java.util.Map)}, for both size restrictions and content
   *
   * @throws PushServiceException if an error occurred
   *
   * @see #sendPushToTokens(java.util.List, java.util.Map)
   */
  void broadcastPush(Map<String, String> pairs) throws PushServiceException;

  /**
   * get all of the tokens for each of the given users
   *
   * @param users the users for which to get tokens
   * @return a mapping from the user to a set containing all of the tokens registered to that user
   *
   * @throws DatastoreException if an error occurred
   */
  Map<String, List<TokenAndType>> getAllTokensForUsers(List<String> users) throws DatastoreException;

  /**
   * remove a token from the list of registered tokens
   * @param token the token to remove
   *
   * @throws DatastoreException if an error occurred
   */
  void removeToken(TokenAndType token) throws DatastoreException;

  /**
   * get the devices that can be sent to with the given payload. this method is useful to use as a check before
   * calling {@link #sendPushToTokens(java.util.List, java.util.Map)} or
   * {@link #sendPushToUsers(java.util.List, java.util.Map)}
   * @param pairs the proposed payload to test
   * @return a set of the <code>TokenType</code>s representing the devices that could be sent to
   * @throws PushServiceException if an error occurred
   */
  Set<TokenType> getSendableDevicesForPayload(Map<String, String> pairs) throws PushServiceException;

  /**
   * register a token for a given user
   * @param username the user to which to register the token
   * @param token the token to register
   * @throws DatastoreException if an error occurred
   */
  void registerTokenForUser(String username, TokenAndType token) throws DatastoreException;

  /**
   * get all of the tokens that are expired for this app
   * @param clear if true, remove all of the returned tokens after this method returns
   * @return a mapping from expired token to expiration time of that token, represented in milliseconds since
   * January 1, 1970 UTC
   * @throws DatastoreException if an error occurred
   */
  Map<TokenAndType, Long> getAllExpiredTokens(boolean clear) throws DatastoreException;

  /**
   * Send a push notification to all iOS devices identified by the tokens provided. This is a shorthand for
   * <code>sendPush(tokens, badge, sound, alert, true)</code>
   *
   * @param tokens the device tokens to which the push notification should be sent
   * @param badge  the badge for the push notification; specifies the number to be displayed with the app icon
   * @param sound  the sound to be played by the devices
   * @param alert  the text of the alert to be displayed by the devices
   * @throws PushServiceException if an error occurs
   * @deprecated use {@link #sendPushToTokens(java.util.List, java.util.Map)} instead. This method only pushes to iOS devices
   */
  @Deprecated
  void sendPush(List<String> tokens, int badge, String sound, String alert) throws PushServiceException;

  /**
   * Send a push notification to all iOS devices identified by the recipients list. The meaning of the entries in the
   * recipients list is controlled by the <code>recipientsAreTokens</code> parameter. If true, the recipients list
   * is considered to consist of device tokens. If false, the recipients list will be considered to be user IDs. In
   * either case, tokens need to be registered via the {@link #registerToken(String, String)} call, before they can be
   * used.
   *
   * @param recipients the device tokens to which the push notification should be sent
   * @param badge  the badge for the push notification; specifies the number to be displayed with the app icon
   * @param sound  the sound to be played by the devices
   * @param alert  the text of the alert to be displayed by the devices
   * @param recipientsAreTokens the type of entries in the recipients list. true if specifying tokens, false for users
   * @throws PushServiceException if an error occurs
   * @deprecated use {@link #sendPushToUsers(java.util.List, java.util.Map)} instead. This only sends to iOS devices
   */
  @Deprecated
  void sendPush(List<String> recipients, int badge, String sound, String alert, boolean recipientsAreTokens)
    throws PushServiceException;

  /**
   * Broadcast a push notification to all devices running this app.
   *
   * @param badge the badge for the push notification; specifies the number to be displayed with the app icon
   * @param sound the sound to be played by the devices
   * @param alert the text of the alert to be displayed by the devices
   * @throws PushServiceException if an error occurs
   * @deprecated use {@link #broadcastPush(java.util.Map)} instead. this method only broadcasts to iOS devices
   */
  @Deprecated
  void broadcastPush(int badge, String sound, String alert) throws PushServiceException;

  /**
   * Get the tokens, which have been reported as expired by Apple's feedback service
   * @param clear if true, the set of expired tokens will be cleared after the call. if false, the current token set
   * will be retained on the server
   * @return A list of expired tokens, represented as a <code>Map<String, Long></code>, where the token is the key,
   * and the value is the expiration time, represented as milliseconds since January 1, 1970 UTC
   * @throws DatastoreException if an error occurs
   * @deprecated use {@link #getAllExpiredTokens(boolean)} instead. This method only gets/removes iOS expired tokens,
   */
  @Deprecated
  Map<String, Long> getExpiredTokens(boolean clear) throws DatastoreException;

  /**
   * Assign a push token to the specified user. After a token is registered, it may be used to push messages to the
   * user. If the username is null, or an empty string, the token will be registered without a username.
   *
   * @param username a username to associate with the specified token
   * @param token    iOS device token
   * @throws DatastoreException if an error occurs
   * @deprecated use {@link #registerTokenForUser(String, TokenAndType)} instead. This method only registers iOS tokens
   */
  @Deprecated
  void registerToken(String username, String token) throws DatastoreException;

  /**
   * Finds the device push tokens for the given user list. Will not return the tokens, which were marked as expired by
   * the feedback service. Use {@link #getExpiredTokens(boolean)} to get the expired tokens.
   * @param users list of user IDs
   * @return a map of user IDs to push tokens. If no token is registered for a user, then the user ID will not be
   * included in the returned map
   * @throws DatastoreException if an error occurs
   * @deprecated use {@link #getAllTokensForUsers(java.util.List)} instead. This method only gets iOS tokens.
   */
  @Deprecated
  Map<String, String> getTokensForUsers(List<String> users) throws DatastoreException;

  /**
   * Removes the token from the list of registered tokens.
   *
   * @param token token to be removed
   * @throws DatastoreException if an error occurs
   * @deprecated use {@link #removeToken(TokenAndType)} instead. This method can only remove iOS tokens.
   */
  @Deprecated
  void removeToken(String token) throws DatastoreException;
}