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

/**
 * A "within" query for searching within a rectangular area
 */
public class SMWithinBox extends SMCondition {
  private final String field;
  private final SMDouble latLL;
  private final SMDouble lonLL;
  private final SMDouble latUR;
  private final SMDouble lonUR;

  /**
   * Create a new SMWithinBox query
   *
   * @param field the geoField to query
   * @param latLL the latitude of the lower-left corner of the bounding rectangle
   * @param lonLL the longitude of the lower-left corner of the bounding rectangle
   * @param latUR the latitute of the upper-right corner of the bounding rectangle
   * @param lonUR the longitude of the upper-right corner of the bounding rectangle
   */
  public SMWithinBox(String field, SMDouble latLL, SMDouble lonLL, SMDouble latUR, SMDouble lonUR)  {
    this.field = field;
    this.latLL = latLL;
    this.lonLL = lonLL;
    this.latUR = latUR;
    this.lonUR = lonUR;
  }

  /**
   * Create a new SMWithinBox query
   *
   * @param field the geoField to query
   * @param latLL the latitude of the lower-left corner of the bounding rectangle
   * @param lonLL the longitude of the lower-left corner of the bounding rectangle
   * @param latUR the latitute of the upper-right corner of the bounding rectangle
   * @param lonUR the longitude of the upper-right corner of the bounding rectangle
   */
  public SMWithinBox(String field, double latLL, double lonLL, double latUR, double lonUR)  {
    this.field = field;
    this.latLL = new SMDouble(latLL);
    this.lonLL = new SMDouble(lonLL);
    this.latUR = new SMDouble(latUR);
    this.lonUR = new SMDouble(lonUR);
  }


  public String getField() {
    return field;
  }

  public SMDouble getLatLL() {
    return latLL;
  }

  public SMDouble getLonLL() {
    return lonLL;
  }

  public SMDouble getLatUR() {
    return latUR;
  }

  public SMDouble getLonUR() {
    return lonUR;
  }

  @Override
  public String toString() {
    return String.format("%s within box between %s, %s and %s, %s", field, latLL.toString(), lonLL.toString(), latUR.toString(), lonUR.toString());
  }
}
