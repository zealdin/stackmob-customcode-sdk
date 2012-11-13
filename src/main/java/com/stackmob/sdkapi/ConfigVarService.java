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

import com.stackmob.core.ConfigVarServiceException;

/**
 * ConfigVarService allows you to access config vars, which are information from third party modules,
 * usually authentication credentials, that let you interact with their services.
 */
public abstract class ConfigVarService {

    /**
     * Get the config var for the given key. This method is only usable from
     * module code, and gets config vars for the current module.
     * @param key the key to lookup
     * @throws ConfigVarServiceException if the config vars can't be reached
     * @return the value associated with that key or null
     */
    public abstract String get(String key) throws ConfigVarServiceException;

    /**
     * Get the config var for the given key and module. This method is only
     * usable from app custom code, and can load config vars for any module.
     * Use this to get the information you need to access third-party module
     * services from your app's custom code to write custom logic or mashups.
     * @param key the key to lookup. You can find the names of config vars in
     *            the dashboard page for each module.
     * @param moduleName find the module name in the url of the dashboard
     *                   page for each module
     * @throws ConfigVarServiceException if the config vars can't be reached
     * @return the value associated with that key or null
     */
    public abstract String get(String key, String moduleName) throws ConfigVarServiceException;

}
