# Stackmob Custom Code SDK

The StackMob Custom Code SDK supports both Java and Scala custom code. Ruby support is also available via a Heroku add-on. Please see the <a href="https://github.com/stackmob/stackmob-ruby">stackmob-ruby</a> SDK for more details.

## Using the SDK

The Custom Code SDK is available via the Central Maven Repository. Below are examples of using the Custom Code SDK with Maven and sbt. It is also available for download <a href="http://search.maven.org/remotecontent?filepath=com/stackmob/customcode/0.1.0/customcode-0.1.0.jar">here</a>.

**Maven**

```xml
<dependency>
  <groupId>com.stackmob</groupId>
  <artifactId>customcode</artifactId>
  <version>0.1.0</version>
  <scope>provided</scope>
</dependency>
```

**sbt**

```scala
libraryDependencies += "com.stackmob" % "customcode" % "0.1.0" % "provided"
```

## Javadocs

Javadocs are available <a href="http://stackmob.github.com/stackmob-customcode-sdk/0.1.0/apidocs/">here</a>.

## Extend your REST API

### Why should I extend my REST API?

StackMob generates your persistence layer and a REST API for you automatically via the <a href="https://www.stackmob.com/platform/api/objects/create">Object Model creation process</a>, but you'll likely want to do more than just save and fetch data. The SDK enables you to run custom server-side code, interact with the datastore, and extend the REST API to support your own custom methods.

### Write a new REST API method

Once the JAR file is in your classpath, let's try a "Hello World" example. We will be extending your REST API so that calling:

    http://yourclient.mob1.stackmob.com/api/1/yourapp/hello_world_example

will return a JSON object:

```json
{"greeting": "hello world!"}
```

**Java**

```java

package com.stackmob.example.helloworld;

import com.stackmob.core.customcode.CustomCodeMethod;
import com.stackmob.core.rest.ProcessedAPIRequest;
import com.stackmob.core.rest.ResponseToProcess;
import com.stackmob.sdkapi.SDKServiceProvider;
import java.util.HashMap;

public class HelloWorldExample implements CustomCodeMethod {

  /**
   * This method simply returns the name of your method that we'll expose over REST for
   * this class. Although this name can be anything you want, we recommend replacing the
   * camel case convention in your class name with underscores, as shown here.
   *
   * @return the name of the method that should be exposed over REST
   */
  @Override
  public String getMethodName() {
    return "hello_world_example";
  }

  /**
   * This method returns the parameters that your method should expect in its query string.
   * Here we are using no parameters, so we just return an empty list.
   *
   * @return a list of the parameters to expect for this REST method
   */
  @Override
  public List<String> getParams() {
    return Arrays.asList();
  }

  /**
   * This method contains the code that you want to execute.
   *
   * @return the response
   */
  @Override
  public ResponseToProcess execute(ProcessedAPIRequest request, SDKServiceProvider serviceProvider) {
    return new ResponseToProcess(200, new HashMap<String, String>("greeting", "hello world!"));
  }

}    
```

**Scala**

```scala
package com.stackmob.example.helloworld

import com.stackmob.core.customcode.CustomCodeMethod
import com.stackmob.sdkapi.SDKServiceProvider
import com.stackmob.core.rest.{ResponseToProcess, ProcessedAPIRequest}
import java.util.{Arrays, List}
import scala.collection.JavaConversions._

class HelloWorldExample extends CustomCodeMethod {

  /**
   * This method simply returns the name of your method that we'll expose over REST for
   * this class. Although this name can be anything you want, we recommend replacing the
   * camel case convention in your class name with underscores, as shown here.
   *
   * @return the name of the method that should be exposed over REST
   */
  override def getMethodName: String = {
    "hello_world_example"
  }

  /**
   * This method returns the parameters that your method should expect in its query string.
   * Here we are using no parameters, so we just return an empty list.
   *
   * @return a list of the parameters to expect for this REST method
   */
  override def getParams: List[String] = {
    Arrays.asList()
  }

  /**
   * This method contains the code that you want to execute.
   *
   * @return the response
   */
  override def execute(request: ProcessedAPIRequest, serviceProvider: SDKServiceProvider): ResponseToProcess = {
    new ResponseToProcess(200, Map("greeting" -> "hello world!"))
  }

}
```

### Register your new REST API method

In order to register this method as a valid REST API endpoint, create a class that extends `JarEntryObject` and include your custom code method in the list of returned methods.

**Java**

```java
package com.stackmob.example.helloworld;

import com.stackmob.core.jar.JarEntryObject;
import com.stackmob.core.customcode.CustomCodeMethod;
import com.stackmob.example.helloworld.HelloWorldExample;
import java.util.List;
import java.util.ArrayList;

public class EntryPointExtender extends JarEntryObject {

  @Override
  public List<CustomCodeMethod> methods() {
    List<CustomCodeMethod> list = new ArrayList<CustomCodeMethod>();
    list.add(new HelloWorldExample());
    return list;
  }

}
```

**Scala**

```scala
package com.stackmob.example.helloworld

import com.stackmob.core.customcode.CustomCodeMethod
import com.stackmob.core.jar.JarEntryObject

class EntryPointExtender extends JarEntryObject {

  override def methods: List[CustomCodeMethod] = {
    List(new HelloWorldExample)
  }
    
}
```

Congratulations! You've just extended your REST API! Let's get it packaged up and ready to upload to StackMob.

## Define the JAR Manifest

StackMob requires that your custom code JAR have a manifest with the Main-Class attribute defined. The main class *must* extend `JarEntryObject`.

**Maven**

Add this to the pom.xml file, in the build plugins section:

```xml
<build>
  ...
  <plugins>
    ...
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-jar-plugin</artifactId>
      <configuration>
        <archive>
          <manifest>
            <mainClass>com.stackmob.example.helloworld.EntryPointExtender</mainClass>
          </manifest>
        </archive>
      </configuration>
    </plugin>
  </plugins>
</build>
```

**Ant**

Add this to the build.xml file:

```xml
<jar destfile="${dist}/target/HelloWorldExample.jar" basedir="${build}/classes" excludes="**Tests.class">
  <manifest>
    <attribute name="Main-Class" value="com.stackmob.example.helloworld.EntryPointExtender"/>
  </manifest>
</jar>
```

We recommend that you use a build tool like Ant or Maven, but if you decide to use the command line to build your code, create a file called JarManifest with nothing but this line in it:

    Main-Class: com.stackmob.example.helloworld.EntryPointExtender

Then, in the same directory as that file, execute these three commands on the command line:

    > mkdir -p target/classes
    > javac -d target/classes -classpath CLASSPATH SOURCE_FILES_SPACE_DELIMITED
    > jar cfm HelloWorldExample.jar JarManifest -C target/classes com/stackmob/example/helloworld/*.class

After the JAR is built, let's do a quick sanity check. First, unzip the JAR into a new directory and change to that directory:

    > unzip HelloWorldExample.jar -d JarUnzipped
    > cd JarUnzipped

Then, ensure that the Manifest is correct:

    > cd META-INF
    > cat MANIFEST.MF | grep Main-Class

The output of the last command should look like this:

    Main-Class: com.stackmob.example.helloworld.EntryPointExtender

Now, let's make sure all the classes were packaged correctly:

    > cd ..
    > ls -la com/stackmob/example/helloworld

If the output of the `ls` command showed `EntryPointExtender.class`, then your JAR is correct and ready to upload and the unzipped JAR contents can be deleted:

    > cd ..
    > rm -rf JarUnzipped

### Uploading your JAR to StackMob

Once you have your custom methods written, package it as a JAR so that it can be uploaded to StackMob. Upon uploading, StackMob will immediately process and roll out the code to your application's sandbox environment. To test your new REST API method, use the <a href="https://www.stackmob.com/platform/api/console">console</a>.

## Fetch Parameters

Each parameter returned in the `getParams` method of a custom code method can be accessed at runtime via:

**Java**

```java
public ResponseToProcess execute(ProcessedAPIRequest request, SDKServiceProvider serviceProvider) {
  String data = request.getParams().get("my_param");
  ...
}
```

**Scala**

```scala
override def execute(request: ProcessedAPIRequest, serviceProvider: SDKServiceProvider): ResponseToProcess = {
  val data = request.getParams.get("timeout_ms")
  ...
}
```

## Datastore Service

The datastore service provides server-side access to the StackMob datastore and can be used to create REST API extensions.

### Interact with the datastore

The example below shows how to set a high score on a user model via the URL:

    http://yourclient.mob1.stackmob.com/api/1/yourapp/set_high_score?username=user_10&score=12345.

Note, this example only shows the execute method of a `CustomCodeMethod` subclass.

**Java**

```java
@Override
public ResponseToProcess execute(ProcessedAPIRequest request, SDKServiceProvider serviceProvider) {
  String username = request.getParams().get("username");
  Integer score = Integer.parseInt(request.getParams().get("score"));

  if (username == null || username.isEmpty() || score == null) {
    HashMap<String, String> errParams = new HashMap<String, String>();
    errParams.put("error", "one or both the username or score was empty or null");
    return new ResponseToProcess(400, errParams); // http 400 - bad request
  }

  // get the datastore service and assemble the query
  DatastoreService datastoreService = serviceProvider.getDatastoreService();
  HashMap<String, List<String>> query = new HashMap<String, List<String>>();
  query.put("username", Arrays.asList(username));

  // execute the query
  List<Map<String, Object>> result;
  try {
    boolean newUser = false;
    boolean updated = false;

    result = datastoreService.readObjects("users", query);

    Map<String, Object> userMap;

    // user was in the datastore, so check the score and update if necessary
    if (result != null && result.size() == 1) {
      userMap = result.get(0);
    } else {
      userMap = new HashMap<String, Object>();
      userMap.put("username", username);
      userMap.put("score", new Integer(0));
      newUser = true;
    }

    int oldScore = Integer.decode(userMap.get("score").toString());

    // if it was a high score, update the datastore
    if (oldScore != null && oldScore < score) {
      userMap.put("score", score);
      updated = true;
    }

    if(newUser) {
      datastoreService.createObject("users", userMap);
    } else if(updated) {
      datastoreService.updateObject("users", username, userMap);
    }

    Map<String, Object> returnMap = new HashMap<String, Object>();
    returnMap.put("updated", new Boolean(updated));
    returnMap.put("newUser", new Boolean(newUser));
    returnMap.put("username", username);
    
    return new ResponseToProcess(200, returnMap);
  } catch (InvalidSchemaException e) {
    HashMap<String, String> errMap = new HashMap<String, String>();
    errMap.put("error", "invalid_schema");
    errMap.put("detail", e.toString());
    return new ResponseToProcess(500, errMap); // http 500 - internal server error
  } catch (DatastoreException e) {
    HashMap<String, String> errMap = new HashMap<String, String>();
    errMap.put("error", "datastore_exception");
    errMap.put("detail", e.toString());
    return new ResponseToProcess(500, errMap); // http 500 - internal server error
  } catch(Exception e) {
    HashMap<String, String> errMap = new HashMap<String, String>();
    errMap.put("error", "unknown");
    errMap.put("detail", e.toString());
    return new ResponseToProcess(500, errMap); // http 500 - internal server error
  }

}
```

**Scala**

```scala
override def execute(request: ProcessedAPIRequest, serviceProvider: SDKServiceProvider): ResponseToProcess = {
  val username = request.getParams.get("username")
  val score = request.getParams.get("score").toInt

  if (username == null || username.isEmpty || score == null) { // http 400 - bad request
    return new ResponseToProcess(400, Map("error" -> "one or both the username or score was empty or null"))
  }

  // get the datastore service and assemble the query
  val datastoreService = serviceProvider.getDatastoreService

  try {
    // execute the query
    val result = datastoreService.readObjects("users", Map("username" -> Arrays.asList(username)))

    // check if the user is in the datastore
    val (userMap: Map[String, Object], newUser) = result match {
      case _ if result != null && result.size == 1 => (result.get(0), false)
      case _ => (Map("username" -> username), true)
    }

    // if it's a new high score, the datastore needs to be updated
    val updated = userMap.get("score") match {
      case _ @ s if s != null && s.toString.toInt < score => true
      case _ => false
    }

    if (newUser) {
      datastoreService.createObject("users", if (updated) userMap + ("score" -> new Integer(score)) else userMap)
    } else if (updated) {
      datastoreService.updateObject("users", username, userMap + ("score" -> new Integer(score)))
    }

    new ResponseToProcess(200, Map("updated" -> updated, "newUser" -> newUser, "username" -> username)) // http 200 - ok
  } catch { // http 500 - internal server error
    case e: InvalidSchemaException => new ResponseToProcess(500, Map("error" -> "invalid_schema", "detail" -> e.toString))
    case e: DatastoreException => new ResponseToProcess(500, Map("error" -> "datastore_exception", "detail" -> e.toString))
    case e => new ResponseToProcess(500, Map("error" -> "unknown", "detail" -> e.toString))
  }

}
```

## Copyright

Copyright 2011 StackMob

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
