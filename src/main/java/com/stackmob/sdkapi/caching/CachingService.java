/**
 * Copyright 2012-2013 StackMob
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
package com.stackmob.sdkapi.caching;

import java.nio.charset.Charset;

import com.stackmob.sdkapi.caching.exceptions.*;

/**
 * CachingService provides fast, in memory access to data, like the results of DataService queries.
 * Data are stored as key/value pairs. A single key/value pair is assigned a TTL (time to live) and will be stored for
 * no longer than that duration. Additionally, a key/value pair can be evicted at any time before that duration has
 * passed. If an eviction happens, CachingService will treat the data as non-existent.
 *
 * Here's a pattern illustrating basic usage of CachingService in your CustomCodeMethod's <code>execute</code> method:
 * <code>
 *     CachingService cachingService = sdkServiceProvider.getCachingService();
 *     String json =  null;
 *     try {
 *         json = cachingService.getString("largeQuery", CachingService.utf8Charset());
 *     } finally {}
 *     if(json == null) {
 *         json = executeLargeDatastoreQuery();
 *         //cache the result of the large query for 5 seconds
 *         cachingService.setString("largeQuery", json, CachingService.utf8Charset, 5000);
 *     }
 * </code>
 */
public abstract class CachingService {
    public static final Charset utf8Charset = Charset.forName("UTF-8");

    /**
     * get the value for the given key, in String format
     * @param key the key to get
     * @param charset the charset to use to build the resultant
     * @return the String for that key, or null if the key didn't exist
     * @throws com.stackmob.sdkapi.caching.exceptions.TimeoutException if there was a timeout communicating with the cache
     * @throws com.stackmob.sdkapi.caching.exceptions.RateLimitedException if there was a rate limit imposed on the cache get request
     * @throws com.stackmob.sdkapi.caching.exceptions.DataSizeException if the size of the key or its value in the cache was over the Stackmob-defined limit
     */
    public String getString(String key, Charset charset) throws TimeoutException, RateLimitedException, DataSizeException {
        byte[] rawBytes = getBytes(key);
        if(rawBytes != null) {
            return new String(rawBytes, charset);
        } else {
            return null;
        }
    }

    /**
     * alias for <code>getString(key, CachingService.utf8Charset());</code>
     * @param key the key to fetch
     * @return the value for <code>key</code>
     * @throws com.stackmob.sdkapi.caching.exceptions.TimeoutException if there was a timeout communicating with the cache
     * @throws com.stackmob.sdkapi.caching.exceptions.RateLimitedException if there was a rate limit imposed on the cache get request
     * @throws DataSizeException if the size of the key or its value in the cache was over the Stackmob-defined limit
     */
    public String getString(String key) throws TimeoutException, RateLimitedException, DataSizeException {
        return getString(key, CachingService.utf8Charset);
    }

    /**
     * get the value for the given key, in raw byte format
     * @param key the key to get
     * @return the byte array for that key, or null if the key didn't exist
     * @throws com.stackmob.sdkapi.caching.exceptions.TimeoutException if there was a timeout communicating with the cache
     * @throws com.stackmob.sdkapi.caching.exceptions.RateLimitedException if there was a rate limit imposed on the cache get request
     * @throws DataSizeException if the size of the key or its value in the cache was over the Stackmob-defined limit
     */
    public abstract byte[] getBytes(String key) throws TimeoutException, RateLimitedException, DataSizeException;

    /**
     * store the given key/value pair. convenience method for <code>setBytes(key, value.getBytes(charset), ttlMilliseconds)</code>
     * @param key the key to store
     * @param value the value to store for the given key
     * @param charset the charset to use
     * @param ttlMilliseconds the TTL for this key/value pair, in milliseconds
     * @return true if the set succeeded, false otherwise
     */
    public Boolean setString(String key, String value, Charset charset, long ttlMilliseconds) throws TimeoutException, RateLimitedException, DataSizeException {
        return setBytes(key, value.getBytes(charset), ttlMilliseconds);
    }

    /**
     * alias for <code>setString(key, value, CachingService.utf8Charset(), ttlMilliseconds)</code>
     * @param key the key to store
     * @param value the value to store for the given key
     * @param ttlMilliseconds the TTL for this key/value pair, in milliseconds
     * @return true if the set succeeded, false otherwise
     * @throws com.stackmob.sdkapi.caching.exceptions.TimeoutException if there was a timeout communicating with the cache
     * @throws com.stackmob.sdkapi.caching.exceptions.RateLimitedException if there was a rate limit impose on this cache get request
     * @throws DataSizeException if the size of the key or its value was over the Stackmob defined limit
     */
    public Boolean setString(String key, String value, long ttlMilliseconds) throws TimeoutException, RateLimitedException, DataSizeException {
        return setString(key, value, CachingService.utf8Charset, ttlMilliseconds);
    }

    /**
     * store the given key/value pair
     * @param key the key to store
     * @param value the value to store for <code>key</code>
     * @param ttlMilliseconds the TTL for this key/value pair, in milliseconds
     * @return true if the set succeeded, false otherwise
     * @throws com.stackmob.sdkapi.caching.exceptions.TimeoutException if there was a timeout communicating with the cache
     * @throws com.stackmob.sdkapi.caching.exceptions.RateLimitedException if there was a rate limit imposed1 on this cache get request
     * @throws DataSizeException if the size of the key or its value was over the Stackmob defined limit
     */
    public abstract Boolean setBytes(String key, byte[] value, long ttlMilliseconds) throws TimeoutException, RateLimitedException, DataSizeException;
}
