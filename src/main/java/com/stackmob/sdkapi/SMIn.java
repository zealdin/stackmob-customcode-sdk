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

import java.util.List;

/**
 * An "in" query
 */
public class SMIn extends SMCondition {
  private final String field;
  private final List<? extends SMValue> values;

  /**
   * Creates a new SMIn query
   * @param field the field to query
   * @param values the set of possible values to match
   */
  public SMIn(String field, List<? extends SMValue> values) {
    this.field = field;
    this.values = values;
  }

  /**
   * Creates a new SMIn query
   * @param field the field to query
   * @param values the set of possible values to match
   */
  public SMIn(String field, SMList values) {
    this.field = field;
    this.values = (List<SMValue>)values.getValue();
  }

  public String getField() {
    return field;
  }

  public List<? extends SMValue> getValues() {
    return values;
  }

  @Override
  public String toString() {
    if (values.size() == 0) 
      return field + " in []";
    StringBuilder result = new StringBuilder();
    result.append(field);
    result.append(" in [");
    for(SMValue value : values) {
      result.append(value.toString());
      result.append(", ");
    }
    return result.substring(0, result.length() - 2) + "]";
  }
}
