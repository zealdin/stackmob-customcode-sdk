# Stackmob Custom Code SDK (Release Notes)

### 0.5.3
* Add support for social token unlinking (Facebook and Twitter services)

### 0.5.2
* Add support for module jars
  * Config var access
  * Get the current user schema name

### 0.5.0
* add body parameter to `ProcessedAPIRequest`

### 0.4.4
* Remove Netty dependency and related @deprecated methods
* Add atomic increment support

### 0.4.3
* Correct List<SMValue> to List<? extends SMValue> when appropriate
* Added additional convenience constructors
  * Geo queries can be passed doubles or SMDoubles
  * SMLists can be substituted for List<? extends SMValue>
* Added Http client

### 0.4.2
* Added additional query conditions
  * <, <=, >, >=
  * Geo queries
  * Null
  * Not Equal
* Order-by
* Pagination
* Expand queries
* Relational queries
* Get count of objects for a schema
* Atomic, conditional update one


### 0.4.1 (04/30/12)
* Added additional query functionality
  * Update by query
  * IN query
  * Field filtering

### 0.4.0 (02/28/12)
* Added new DataService, replaces deprecated DatastoreService

### 0.3.0 (10/03/11)
* Added Logger service

### 0.2.0 (09/30/11)
* Push service now supports Android

### 0.1.0 (09/15/11)
* Initial version of the StackMob Custom Code SDK
  * Datastore service
  * Push service
  * Facebook service
  * Twitter service
