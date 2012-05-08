package com.stackmob.sdkapi;

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
   * Creates a new object in the datastore. Each such object should be represented as a map of field names to objects,
   * where each sub-object is a List, Map, String, Long, or Double depending on the type of the field in question.
   *
   * @param schema the name of the schema which contains the relation to the object to be inserted
   * @param objectId the ID value of the object to relate to the inserted object
   * @param relatedField the field name of the relationship
   * @param relatedObjectToCreate the related object to insert
   * @return the object created
   * @throws InvalidSchemaException if the object to create does not match the relevant schema
   * @throws DatastoreException if the connection to the datastore fails or the datastore encounters an error
   */
  SMObject createRelatedObject(String schema, String objectId, String relatedField, SMObject relatedObjectToCreate)
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
   * Reads a list of objects matching the given query fields from the datastore, returning only the specified fields
   *
   * @param schema the name of the relevant object model
   * @param conditions the list of conditions which comprise the query
   * @param fields the list of fields to be returned; regardless of what is specified, the id will always be returned
   * @return a list of all documents matching the query
   * @throws InvalidSchemaException if the schema specified does not exist, or the query is incompatible with the schema
   * @throws DatastoreException if the connection to the datastore fails or the datastore encounters an error
   */
  List<SMObject> readObjects(String schema, List<SMCondition> conditions, List<String> fields)
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
   * Updates all objects matching the given query in the datastore
   *
   * @param schema the name of the relevant object model; must be a type already declared for the current application
   * @param query the query used to find objects to update
   * @param updateActions the actions to take on the object being updated
   * @throws InvalidSchemaException if the schema does not exist, or the update actions are incompatible with it
   * @throws DatastoreException if the connection to the datastore fails or the datastore encounters an error
   */
  void updateObjects(String schema, List<SMCondition> conditions, List<SMUpdate> updateActions)
          throws InvalidSchemaException, DatastoreException;

  /**
   * Adds the specified IDs to the specified relationship
   * 
   * @param schema the name of the relevant object model; must be a type already declared for the current application
   * @param objectId the id of the object to which relations should be added
   * @param relatedIds the ids of all objects to be related to the specified parent object
   * @throws InvalidSchemaException if the schema does not exist, or the update actions are incompatible with it
   * @throws DatastoreException if the connection to the datastore fails or the datastore encounters an error
   */
  void addRelatedObjects(String schema, String objectId, List<String> relatedIds)
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
   * Removes any number of related objects from a relationship. May also delete the objects removed from the relationship.
   * @param schema the name of the relevant object model; must be a type already declared for the current application
   * @param objectId the id of the object to which relations should be removed
   * @param relatedIds the ids of the objects to be removed from the relationship
   * @param cascadeDelete should be set to true if and only if you wish to also delete from the datastore all objects removed from the relationship
   * @throws InvalidSchemaException if the object model specified does not exist
   * @throws DatastoreException if the connection to the datastore fails or the datastore encounters an error
   */
  void removeRelatedObjects(String schema, String objectId, List<String> relatedIds, boolean cascadeDelete) 
          throws InvalidSchemaException, DatastoreException;

  /**
   * Get the number of objects in a schema
   *
   * @param schema the name of the object model to count
   * @return the number of objects in the datastore for the given object model
   * @throws InvalidSchemaException if the object model specified does not exist
   * @throws DatastoreException if the connection to the datastore fails or the datastore encounters an error
   */
  long countObjects(String schema) throws InvalidSchemaException, DatastoreException;

  /**
   * Retrieves a list of the object models declared for the current application.
   *
   * @return the set of all valid object model names
   * @throws ConnectException if the list of object models cannot be retrieved
   */
  Set<String> getObjectModelNames() throws ConnectException;
}
