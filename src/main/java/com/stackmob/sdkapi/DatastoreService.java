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

import com.stackmob.core.DatastoreException;
import com.stackmob.core.InvalidSchemaException;

import java.net.ConnectException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The datastore service.
 */
public interface DatastoreService {
  
  /**
   * Creates a new object in the datastore. Each such object should be represented as a map of field names to objects,
   * where each sub-object is a List, Map, String, Long, or Double depending on the type of the field in question.
   *
   * @param modelName the name of the relevant object model; must be a type already declared for the current application
   * @param toCreate the object to create; must match the schema declared for the relevant object type
   * @return the object created
   * @throws InvalidSchemaException if the object model specified does not exist, or the object to create does not match
   * the expected schema for that object model
   * @throws DatastoreException if the connection to the datastore fails or the datastore encounters an error
   */
  Map<String, Object> createObject(String modelName, Map<String, Object> toCreate)
    throws InvalidSchemaException, DatastoreException;

  /**
   * Reads a list of objects matching the given query fields from the datastore.
   *
   * @param modelName the name of the relevant object model; must be a type already declared for the current application
   * @param queryFields a map from fields to query to the query values; takes the form of a map from field names to
   * lists of possible string values for that field
   * @return a list of all documents matching the query; each such document is represented as a map of field names to
   * objects, where each sub-object is a List, Map, String, Long, or Double depending on the type of the field in
   * question
   * @throws InvalidSchemaException if the object model specified does not exist
   * @throws DatastoreException if the connection to the datastore fails or the datastore encounters an error
   */
  List<Map<String, Object>> readObjects(String modelName, Map<String, List<String>> queryFields)
    throws InvalidSchemaException, DatastoreException;

  /**
   * Updates an object in the datastore. Each such object should be represented as a map of field names to objects,
   * where each sub-object is a List, Map, String, Long, or Double depending on the type of the field in question.
   *
   * @param modelName the name of the relevant object model; must be a type already declared for the current application
   * @param objectId the id of the object to update
   * @param newValue the fields to update, and their intended values; must not contain the id field of the object
   * @return the updated object
   * @throws InvalidSchemaException if newValue contains illegal types or does not match fields in the object to update,
   * or the object model does not exist
   * @throws DatastoreException if the connection to the datastore fails or the datastore encounters an error
   */
  Map<String, Object> updateObject(String modelName, String objectId, Map<String, Object> newValue)
    throws InvalidSchemaException, DatastoreException;

  /**
   * Deletes an object in the datastore.
   *
   * @param modelName the name of the relevant object model; must be a type already declared for the current application
   * @param objectId the id of the object to delete
   * @return a Boolean object representing success (Boolean.TRUE) or failure (Boolean.FALSE)
   * @throws InvalidSchemaException if the object model specified does not exist
   * @throws DatastoreException if the connection to the datastore fails or the datastore encounters an error
   */
  Boolean deleteObject(String modelName, String objectId) throws InvalidSchemaException, DatastoreException;

  /**
   * Retrieves a list of the object models declared for the current application.
   *
   * @return the set of all valid object model names
   * @throws ConnectException if the list of object models cannot be retrieved
   */
  Set<String> getObjectModelNames() throws ConnectException;
  
}
