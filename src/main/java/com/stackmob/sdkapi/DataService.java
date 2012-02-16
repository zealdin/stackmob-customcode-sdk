package com.stackmob.sdkapi;

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

import com.stackmob.core.DatastoreException;
import com.stackmob.core.InvalidSchemaException;

import java.net.ConnectException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * DataService allows you to interact with your data on StackMob
 */
public interface DataService {
  /**
   * Creates a new object in the datastore. Each such object should be represented as a map of field names to objects,
   * where each sub-object is a List, Map, String, Long, or Double depending on the type of the field in question.
   *
   * @param schema the name of the relevant object schema
   * @param toCreate the object to create; must match the schema declared for the relevant object type
   * @return the object created
   * @throws InvalidSchemaException if the object to create does not match the relevant schema
   * @throws DatastoreException if the connection to the datastore fails or the datastore encounters an error
   */
  SMObject createObject(String schema, SMObject toCreate)
          throws InvalidSchemaException, DatastoreException;


  /**
   * Reads a list of objects matching the given query fields from the datastore.
   *
   * @param schema the name of the relevant object model
   * @param conditions the list of conditions which comprise the query
   * @return a list of all documents matching the query
   * @throws InvalidSchemaException if the schema specified does not exist, or the query is incompatible with the schema
   * @throws DatastoreException if the connection to the datastore fails or the datastore encounters an error
   */
  List<SMObject> readObjects(String schema, List<SMCondition> conditions)
          throws InvalidSchemaException, DatastoreException;

  /**
   * Updates an object in the datastore.
   *
   * @param schema the name of the relevant object model; must be a type already declared for the current application
   * @param id the id of the object to update
   * @param updateActions the actions to take on the object being updated
   * @return the updated object
   * @throws InvalidSchemaException if the schema does not exist, or the update actions are incompatible with it
   * @throws DatastoreException if the connection to the datastore fails or the datastore encounters an error
   */
  SMObject updateObject(String schema, String id, List<SMUpdate> updateActions)
          throws InvalidSchemaException, DatastoreException;

  /**
   * Deletes an object in the datastore.
   *
   * @param schema the name of the relevant object model; must be a type already declared for the current application
   * @param id the id of the object to delete
   * @return a Boolean object representing success (Boolean.TRUE) or failure (Boolean.FALSE)
   * @throws InvalidSchemaException if the object model specified does not exist
   * @throws DatastoreException if the connection to the datastore fails or the datastore encounters an error
   */
  Boolean deleteObject(String schema, String id) throws InvalidSchemaException, DatastoreException;

  /**
   * Retrieves a list of the object models declared for the current application.
   *
   * @return the set of all valid object model names
   * @throws ConnectException if the list of object models cannot be retrieved
   */
  Set<String> getObjectModelNames() throws ConnectException;
}
