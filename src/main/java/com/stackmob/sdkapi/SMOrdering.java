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

public class SMOrdering {

  private final String field;
  private final OrderingDirection direction;

  /**
   * Create a new ordering for filtering result sets
   * @param field the field on which to sort results
   * @param direction the direction in which results should be sorted
   */
  public SMOrdering(String field, OrderingDirection direction) {
    this.field = field;
    this.direction = direction;
  }

  public String getField() {
    return field;
  }

  public OrderingDirection getDirection() {
    return direction;
  }
}
