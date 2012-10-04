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

/**
 * ConfigVarService allows you to access key-value pairs specific to this app.
 */
public abstract class ConfigVarService {

    /**
     * Get the value for the given key. Returns null if the key doesn't exist
     * @param key the key to lookup
     * @return the value associated with that key or null
     */
    public abstract String get(String key);

}
