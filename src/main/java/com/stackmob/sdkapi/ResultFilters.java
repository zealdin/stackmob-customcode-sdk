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
 * Query options for filtering results
 */
public class ResultFilters {
  private final long start;
  private final long end;
  private final List<SMOrdering> ordering;
  private final List<String> fields;

  /**
   * Create a new set of result filters
   * @param start the index (inclusive) of the first record to return, set to zero if you wish to return records from the beginning
   * @param end the index (inclusive) of the last record to return, if -1, all records after the starting index (inclusive) will be returned
   * @param orderings the order in which results should be sorted
   * @param fields the fields to return in the result set
   */
  public ResultFilters(long start, long end, List<SMOrdering> orderings, List<String> fields) {
    this.start = start;
    this.end = end;
    this.ordering = orderings;
    this.fields = fields;
  }

  public long getStart() {
    return start;
  }

  public long getEnd() {
    return end;
  }

  public List<SMOrdering> getOrderings() {
    return ordering;
  }

  public List<String> getFields() {
    return fields;
  }
}
