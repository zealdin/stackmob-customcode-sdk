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

/**
 * The logger service.
 */
public interface LoggerService {

  /**
   * Log a message at the TRACE level.
   *
   * @param msg the message to be logged
   */
  public void trace(String msg);

  /**
   * Log a message at the TRACE level.
   *
   * @param msg the message to be logged
   * @param t the throwable to be logged
   */
  public void trace(String msg, Throwable t);

  /**
   * Log a message at the DEBUG level.
   *
   * @param msg the message to be logged
   */
  public void debug(String msg);

  /**
   * Log a message at the DEBUG level.
   *
   * @param msg the message to be logged
   * @param t the throwable to be logged
   */
  public void debug(String msg, Throwable t);

  /**
   * Log a message at the INFO level.
   *
   * @param msg the message to be logged
   */
  public void info(String msg);

  /**
   * Log a message at the INFO level.
   *
   * @param msg the message to be logged
   * @param t the throwable to be logged
   */
  public void info(String msg, Throwable t);

  /**
   * Log a message at the WARN level.
   *
   * @param msg the message to be logged
   */
  public void warn(String msg);

  /**
   * Log a message at the WARN level.
   *
   * @param msg the message to be logged
   * @param t the throwable to be logged
   */
  public void warn(String msg, Throwable t);

  /**
   * Log a message at the ERROR level.
   *
   * @param msg the message to be logged
   */
  public void error(String msg);

  /**
   * Log a message at the ERROR level.
   *
   * @param msg the message to be logged
   * @param t the throwable to be logged
   */
  public void error(String msg, Throwable t);

}
