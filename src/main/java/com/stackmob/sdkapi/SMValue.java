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

import com.stackmob.core.CannotCastSMValueException;

/**
 * An object representing a value stored in the StackMob datastore
 * @param <T> The type of this value, see subclasses for explicit types
 */
public abstract class SMValue<T> {

  protected final T value;

  /**
   * Create a new SMValue
   * @param value the value of this SMValue
   */
  public SMValue(T value) {
    this.value = value;
  }

  /**
   * Get the stored value
   * @return the value of this SMValue
   */
  public T getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SMValue smValue = (SMValue) o;

    return value == null ? smValue.value == null : value.equals(smValue.value);

  }

  @Override
  public int hashCode() {
    return value != null ? value.hashCode() : 0;
  }

  @Override
  public String toString() {
    return value.toString();
  }

  public <U extends SMValue> boolean isA(Class<U> cls) {
    return cls.isAssignableFrom(this.getClass());
  }
    
  public <U extends SMValue> U asA(Class<U> cls) throws CannotCastSMValueException {
    if(isA(cls)) {
      return cls.cast(this);
    }
    else {
      throw new CannotCastSMValueException(cls.getCanonicalName(), this.getClass().getCanonicalName());
    }
  }
}
