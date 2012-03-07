# Stackmob Custom Code SDK

## Why use the Custom Code SDK?

StackMob provides the ability to run server-side custom code that is accessible via user-defined REST API endpoints. For example:

    URL:
    http://api.mob1.stackmob.com/doCustomServerSideOperation?param1=value1&param2=value2
    
    Request Headers:
    Accept: application/vnd.stackmob+json; version=0
    //"version" sets your REST API Version. "0" for Development. "1" and up for Production
    
With the Custom Code SDK, it's possible to extend StackMob's REST API by writing custom server side code. The SDK supports both Java and Scala custom code. Write your code and simply upload your JAR to StackMob. Upon uploading, StackMob will roll out the code and make it accessible via the user-defined REST API endpoint. Ruby support is also available via a Heroku add-on. Please see the <a href="https://github.com/stackmob/stackmob-ruby">stackmob-ruby</a> SDK for more details.

## Including the Custom Code SDK in your Project

StackMob provides various ways for you to include the Custom Code SDK in your project.  You may choose according to your preference.

* Download and include the JAR
* Maven
* sbt (scala)

**JAR**

Latest version: 0.4.0

<a href="http://search.maven.org/remotecontent?filepath=com/stackmob/customcode/0.4.0/customcode-0.4.0.jar">Download the latest JAR</a>.


**Maven**

```xml
<dependency>
  <groupId>com.stackmob</groupId>
  <artifactId>customcode</artifactId>
  <version>0.4.0</version>
  <scope>provided</scope>
</dependency>
```

**sbt**

```scala
libraryDependencies += "com.stackmob" % "customcode" % "0.4.0" % "provided"
```

## Release Notes

Release notes are available <a href="https://github.com/stackmob/stackmob-customcode-sdk/blob/master/RELEASE_NOTES.md">here</a>.

## Javadocs

Javadocs are available <a href="http://stackmob.github.com/stackmob-customcode-sdk/0.4.0/apidocs/">here</a>.


# Extend your REST API

## Why should I extend my REST API?

StackMob generates your persistence layer and a REST API for you automatically via the <a href="https://www.stackmob.com/platform/api/objects/create">Object Model creation process</a>, but you'll likely want to do more than just save and fetch data. The SDK enables you to run custom server-side code, interact with the datastore, and extend the REST API to support your own custom methods.  Extending your REST API lets you define your own server behavior and REST API endpoints, allowing you to customize your application even further.

## Write a new REST API method

Assuming you've downloaded the JAR (see above) and the JAR file is in your classpath, let's try a "Hello World" example. We will be extending your REST API so that calling:

    URL:
    http://api.mob1.stackmob.com/hello_world_example
    
    Request Header:
    Accept: application/vnd.stackmob+json; version=0
    //"version" sets your REST API Version. "0" for Development. "1" and up for Production

will return a JSON object:

```json
{"greeting": "hello world!"}
```

<span class="tab-divider"/>

<span class="tab extendrestapi" title="Java"/>

**Java**

```java

package com.stackmob.example.helloworld;

import com.stackmob.core.customcode.CustomCodeMethod;
import com.stackmob.core.rest.ProcessedAPIRequest;
import com.stackmob.core.rest.ResponseToProcess;
import com.stackmob.sdkapi.SDKServiceProvider;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

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
    Map<String, String> args = new HashMap<String, String>();
    args.put("greeting", "hello world!");
    return new ResponseToProcess(HttpURLConnection.HTTP_OK, args);
  }

}    
```

<span class="tab extendrestapi"/>

<span class="tab extendrestapi" title="Scala"/>

**Scala**

```scala
package com.stackmob.example.helloworld

import com.stackmob.core.customcode.CustomCodeMethod
import com.stackmob.sdkapi.SDKServiceProvider
import com.stackmob.core.rest.{ResponseToProcess, ProcessedAPIRequest}
import java.util.{Arrays, List}
import java.net.HttpURLConnection._
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
    new ResponseToProcess(HTTP_OK, Map("greeting" -> "hello world!"))
  }

}
```

<span class="tab extendrestapi"/>

### Register your new REST API method

In order to register this method as a valid REST API endpoint, create a class that extends `JarEntryObject` and include your custom code method in the list of returned methods.

<span class="tab registermethod" title="Java"/>

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

<span class="tab registermethod"/>

<span class="tab registermethod" title="Scala"/>

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

<span class="tab registermethod"/>

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

Once you have your custom methods written, package it as a JAR so that it can be <a href="https://www.stackmob.com/platform/api/customcode/upload">uploaded to StackMob</a>. Upon uploading, StackMob will immediately process and roll out the code to your application's sandbox environment. To test your new REST API method, use the <a href="https://www.stackmob.com/platform/api/console">console</a>.

# Fetch Parameters

Each parameter returned in the `getParams` method of a custom code method can be accessed at runtime via:

<span class="tab fetchparams" title="Java"/>

**Java**

```java
public ResponseToProcess execute(ProcessedAPIRequest request, SDKServiceProvider serviceProvider) {
  String data = request.getParams().get("my_param");
  ...
}
```

<span class="tab fetchparams"/>

<span class="tab fetchparams" title="Scala"/>

**Scala**

```scala
override def execute(request: ProcessedAPIRequest, serviceProvider: SDKServiceProvider): ResponseToProcess = {
  val data = request.getParams.get("timeout_ms")
  ...
}
```

<span class="tab fetchparams"/>

# Interact with the datastore

The datastore service provides server-side access to the StackMob datastore and can be used to create REST API extensions.

The example below shows how to set a high score on a user model via the URL:

    URL:
    http://api.mob1.stackmob.com/set_high_score?username=user_10&score=12345.
    
    Request Headers:
    Accept: application/vnd.stackmob+json; version=0

Note, this example only shows the execute method of a `CustomCodeMethod` subclass.

<span class="tab datastore" title="Java"/>

**Java**

```java
@Override
public ResponseToProcess execute(ProcessedAPIRequest request, SDKServiceProvider serviceProvider) {
  String username = request.getParams().get("username");
  Integer score = Integer.parseInt(request.getParams().get("score"));

  if (username == null || username.isEmpty() || score == null) {
    HashMap<String, String> errParams = new HashMap<String, String>();
    errParams.put("error", "one or both the username or score was empty or null");
    return new ResponseToProcess(HttpURLConnection.HTTP_BAD_REQUEST, errParams); // http 400 - bad request
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
    return new ResponseToProcess(HttpURLConnection.HTTP_OK, returnMap);
  } catch (InvalidSchemaException e) {
    HashMap<String, String> errMap = new HashMap<String, String>();
    errMap.put("error", "invalid_schema");
    errMap.put("detail", e.toString());
    return new ResponseToProcess(HttpURLConnection.HTTP_INTERNAL_ERROR, errMap); // http 500 - internal server error
  } catch (DatastoreException e) {
    HashMap<String, String> errMap = new HashMap<String, String>();
    errMap.put("error", "datastore_exception");
    errMap.put("detail", e.toString());
    return new ResponseToProcess(HttpURLConnection.HTTP_INTERNAL_ERROR, errMap); // http 500 - internal server error
  } catch(Exception e) {
    HashMap<String, String> errMap = new HashMap<String, String>();
    errMap.put("error", "unknown");
    errMap.put("detail", e.toString());
    return new ResponseToProcess(HttpURLConnection.HTTP_INTERNAL_ERROR, errMap); // http 500 - internal server error
  }

}
```

<span class="tab datastore"/>

<span class="tab datastore" title="Scala"/>

**Scala**

```scala
override def execute(request: ProcessedAPIRequest, serviceProvider: SDKServiceProvider): ResponseToProcess = {
  val username = request.getParams.get("username")
  val score = request.getParams.get("score").toInt

  if (username == null || username.isEmpty || score == null) { // http 400 - bad request
    return new ResponseToProcess(HTTP_BAD_REQUEST, Map("error" -> "one or both the username or score was empty or null"))
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

    new ResponseToProcess(HTTP_OK, Map("updated" -> updated, "newUser" -> newUser, "username" -> username)) // http 200 - ok
  } catch { // http 500 - internal server error
    case e: InvalidSchemaException => new ResponseToProcess(HTTP_INTERNAL_ERROR , Map("error" -> "invalid_schema", "detail" -> e.toString))
    case e: DatastoreException => new ResponseToProcess(HTTP_INTERNAL_ERROR , Map("error" -> "datastore_exception", "detail" -> e.toString))
    case e => new ResponseToProcess(HTTP_INTERNAL_ERROR , Map("error" -> "unknown", "detail" -> e.toString))
  }

}
```

<span class="tab datastore"/>

# Interacting with your Logged-In Users

The SDK allows you to check for a currently logged-in user, direct from the ProcessedAPIRequest. Here's how:

<span class="tab user" title="Java"/>

**Java**

```java
@Override
public ResponseToProcess execute(ProcessedAPIRequest request, SDKServiceProvider serviceProvider) {
  String username = request.getLoggedInUser();

  if (username == null || username.isEmpty()) {
    HashMap<String, String> errParams = new HashMap<String, String>();
    errParams.put("error", "no user is logged in");
    return new ResponseToProcess(HttpURLConnection.HTTP_UNAUTHORIZED, errParams); // http 401 - unauthorized
  }

  Map<String, Object> returnMap = new HashMap<String, Object>();
  returnMap.put("currentLogin", username);
  return new ResponseToProcess(HttpURLConnection.HTTP_OK, returnMap);
}
```

<span class="tab user"/>

<span class="tab user" title="Scala"/>

**Scala**

```scala
override def execute(request: ProcessedAPIRequest, serviceProvider: SDKServiceProvider): ResponseToProcess = {
  val username = request.getLoggedInUser

  if (username == null || username.isEmpty) {
    return new ResponseToProcess(HTTP_UNAUTHORIZED, Map("error" -> "no user is logged in"))
  }

  new ResponseToProcess(HTTP_OK, Map("currentLogin" -> username)) // http 200 - ok
}
```

<span class="tab user"/>

# Push Notifications

The SDK gives access to the StackMob Push Notification service through the PushService class. Here's how to use it:

<span class="tab push" title="Java"/>

**Java**

```java
@Override
public ResponseToProcess execute(ProcessedAPIRequest request, SDKServiceProvider serviceProvider) {
  PushService pushService = serviceProvider.getPushService();

  //register iOS token for John Doe
  TokenAndType iOSToken = new TokenAndType("deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef", TokenType.iOS);
  pushService.registerTokenForUser("JohnDoe", iosToken);

  //register Android token for John Doe
  TokenAndType androidToken = new TokenAndType("androidtoken", TokenType.Android);
  pushService.registerTokenForUser("JohnDoe", androidToken);

  //get all tokens for John Doe
  List<String> users = new ArrayList<String>();
  users.add("JohnDoe");
  Map<String, List<TokenAndType>> tokensForJohnDoe = pushService.getAllTokensForUsers(users);

  //send a push notification just to John Doe's iOS device
  List<TokenAndType> tokensToSendTo = new ArrayList<TokenAndType>();
  tokensToSendTo.add(iosToken);
  Map<String, String> payload = new HashMap<String, String>();
  payload.put("badge", "1");
  payload.put("sound", "customsound.wav");
  payload.put("alert", "Hello from Stackmob!");
  payload.put("other", "stuff");
  pushService.sendPushToTokens(tokensToSendTo, payload);

  //send a push notification to all of John Doe's devices
  pushService.sendPushToUsers(users, payload);

  //broadcast a push notification to EVERYONE - use carefully!
  pushService.broadcastPush(payload);

  //remove the iOS token for John Doe
  pushService.removeToken(iosToken);
  
  Map<String, Object> map = new HashMap<String, Object>();
  map.put("status", "ok");
  return new ResponseToProcess(HttpURLConnection.HTTP_OK, map);
}
```

<span class="tab push"/>

<span class="tab push" title="Scala"/>

**Scala**

```scala
override def execute(request:ProcessedAPIRequest, serviceProvider:SDKServiceProvider): ResponseToProcess = {
  val pushService = serviceProvider.getPushService

  //register iOS token for John Doe
  val iOSToken = new TokenAndType("deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef", TokenType.iOS);
  val androidToken = new TokenAndType("androidtoken", TokenType.Android)
  pushService.registerTokenForUser("JohnDoe", androidToken);

  //get all tokens for John Doe
  val users:JavaList[String] = List("JohnDoe")
  val tokensForJohnDoe = pushService.getAllTokensForUsers(users)

  //send a push notification just to John Doe's iOS device
  val tokensToSendTo:JavaList[TokenAndType] = List(iosToken)
  val payload:JavaMap[String, String] = Map("badge" -> 1,
    "sound" -> "customsound.wav",
    "alert" -> "Hello from Stackmob!",
    "other" -> "stuff")
  pushService.sendPushToTokens(tokensToSendTo, payload)

  //send a push notification to all of John Doe's devices
  pushService.sendPushToUsers(users, payload)

  //broadcast a push notification to EVERYONE - use carefully!
  pushService.broadcastPush(payload)

  //remove the iOS token for John Doe
  pushService.removeToken(iosToken)
  
  new ResponseToProcess(HTTP_OK, Map("status" -> "ok"))
}
```

<span class="tab push"/>

# Logging

The logger service provided by the SDK should be used to log information from within your custom code. Anything logged via the logger service will be accessible via StackMob's web platform.

<span class="tab logging" title="Java"/>

**Java**

```java
@Override
public ResponseToProcess execute(ProcessedAPIRequest request, SDKServiceProvider provider) {
  LoggerService logger = provider.getLoggerService(MyCustomCodeMethod.class);

  logger.debug("log anything");

  try {
    throw new NullPointerException("some npe");
  } catch (NullPointerException e) {
    logger.error(e.getMessage(), e);
  }

  Map<String, Object> map = new HashMap<String, Object>();
  map.put("status", "ok");
  return new ResponseToProcess(HttpURLConnection.HTTP_OK, map);
}
```
<span class="tab logging"/>

<span class="tab logging" title="Scala"/>

**Scala**

```scala
override def execute(request: ProcessedAPIRequest, sdk: SDKServiceProvider): ResponseToProcess = {
  val logger = sdk.getLoggerService(classOf[MyCustomCodeMethod])

  logger.debug("log anything")

  try {
    throw new NullPointerException("some npe")
  } catch {
    case e: NullPointerException => {
      logger.error(e.getMessage, e)
    }
  }

  new ResponseToProcess(HTTP_OK, Map("status" -> "ok"))
}
```
<span class="tab logging"/>

# Copyright

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
