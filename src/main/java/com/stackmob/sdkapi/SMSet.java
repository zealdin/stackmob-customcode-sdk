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
 * A "set" update action. Sets the specified field to a new value.
 */
public class SMSet extends SMUpdate {
  private final String field;
  private final SMValue value;

  /**
   * Creates a new set operation
   * @param field the field on which to operate
   * @param value the new value for that field
   */
  public SMSet(String field, SMValue value) {
    this.field = field;
    this.value = value;
  }

  /**
   * Gets the field on which to execute this action.
   * @return the field
   */
  public String getField() {
    return field;
  }

  /**
   * Gets the new value for the specified field
   * @return the value
   */
  public SMValue getValue() {
    return value;
  }
}
