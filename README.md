# Stackmob Custom Code SDK

## Why use the Custom Code SDK?

With StackMob's server-side custom code, you can write Java/Scala/Clojure code and upload it to StackMob.  Using the StackMob mobile SDK's, you can trigger your code/logic on the server-side and process the returned JSON that you defined in your custom code.  This means that you can not only quickly write your app on the mobile client side, but now you can also quickly write powerful code that runs server-side that can interact with your app!

StackMob already gives you datastore persistence and push services in the cloud.  With server-side custom code, you can write a feature-rich app on a full featured platform with the power of server-side operations.  

# How does it work?

Write a simple Java class implementing the `CustomCodeMethod` interface, build it into a JAR, then <a href="https://www.stackmob.com/platform/api/customcode/upload" target="_blank">upload the JAR to StackMob</a>.

Here's a simple `hello_world` example.  Upon uploading your JAR, StackMob will extend your method to your REST API at:

    http://api.mob1.stackmob.com/hello_world
    
You can then call your code from the mobile iOS, Android, or JS SDKs.  (Or anything else that can hit a REST API!)

The JSON that you define in your server-side code will be returned in the response.


## Hello World - A Custom Code Example

Let's look at server-side code in Java/Scala.  The following code will be found at method `hello_world` and will return the JSON `{ msg: 'hello world!' }`

<span class="tab extendrestapi" title="Java"/>

**Java**

HelloWorldExample.java:

```java

package com.stackmob.example.helloworld;

import com.stackmob.core.customcode.CustomCodeMethod;
import com.stackmob.core.rest.ProcessedAPIRequest;
import com.stackmob.core.rest.ResponseToProcess;
import com.stackmob.sdkapi.SDKServiceProvider;
import java.net.HttpURLConnection;
import java.util.*;

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
    return "hello_world";
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
    args.put("msg", "hello world!");
    return new ResponseToProcess(HttpURLConnection.HTTP_OK, args);
  }

}    
```

<span class="tab extendrestapi"/>

<span class="tab extendrestapi" title="Scala"/>

**Scala**

```scala
package com.stackmob.example.helloworld

import java.util.Arrays
import java.net.HttpURLConnection._
import com.stackmob.core.customcode.CustomCodeMethod
import com.stackmob.sdkapi._
import com.stackmob.core.rest.{ProcessedAPIRequest, ResponseToProcess}
import scala.collection.JavaConverters._

class HelloWorldExample extends CustomCodeMethod {

  /**
   * This method simply returns the name of your method that we'll expose over REST for
   * this class. Although this name can be anything you want, we recommend replacing the
   * camel case convention in your class name with underscores, as shown here.
   *
   * @return the name of the method that should be exposed over REST
   */
  override def getMethodName: String = {
    "hello_world"
  }

  /**
   * This method returns the parameters that your method should expect in its query string.
   * Here we are using no parameters, so we just return an empty list.
   *
   * @return a list of the parameters to expect for this REST method
   */
  override def getParams: java.util.List[String] = {
    Arrays.asList()
  }

  /**
   * This method contains the code that you want to execute.
   *
   * @return the response
   */
  override def execute(request: ProcessedAPIRequest, serviceProvider: SDKServiceProvider): ResponseToProcess = {
    new ResponseToProcess(HTTP_OK, Map("msg" -> "hello world!").asJava)
  }

}
```

<span class="tab extendrestapi"/>

Custom code allows you to even define the returned JSON.  In this case, our simple Hello World example will return:

    { "msg": "Hello, world!" }
    
You can call your server-side custom code from your SDK.  The request will be sent from the client, StackMob will route the call to the appropriate code and execute the code you've written, then StackMob will return the JSON you've defined.

* You'll also need to <a href="https://www.stackmob.com/devcenter/docs/Getting-Started:-Custom-Code-SDK/a-register_your_method">register your method in EntryPointeExtender</a> so that StackMob is aware of it.  Include it in your JAR!

## Calling Server-Side Custom Code from the Mobile SDK

Let's see how client-side SDK code calls and interacts with the server-side custom code:

<span class="tab callcc" title="iOS SDK"/>
**iOS SDK**

```objc
[[StackMob stackmob] get:@"hello_world" withCallback:^(BOOL success, id result) {
    if (success) {
        // result is the JSON as an NSDictionary of "msg" vs. "Hello, world!"
    } else {
    }
}];

```
<span class="tab"/>

<span class="tab callcc" title="Android SDK"/>
**Android SDK**

```java
StackMobCommon.getStackMobInstance().get("hello_world", new StackMobCallback() {
    @Override public void success(String responseBody) {
        //responseBody is "{ \"msg\": \"Hello, world!\" }"
    }
    @Override public void failure(StackMobException e) {
    }
});
```
<span class="tab"/>

<span class="tab callcc" title="JS SDK"/>
**JS SDK**

```javascript
<script type="text/javascript">
  StackMob.customcode('hello_world', {}, {
     success: function(jsonResult) {
       //jsonResult is the JSON object: { "msg": "Hello, world!" }
     },
     
     error: function(failure) {
       //doh!
     }
  });
</script>
```
<span class="tab"/>

You've just seen how you can write server-side code and call it from your mobile application, powerfully extending your ability to build a full featured app.




# QuickStart - Fork Example Custom Code on GitHub

To get you started, we've actually provided an example Custom Code example on GitHub for you.  Feel free to fork it, follow the instructions to build a JAR, then <a href="https://www.stackmob.com/platform/api/customcode/upload" target="_blank">upload that JAR to StackMob</a> to get your first custom code example!

<p><a href="https://github.com/stackmob/stackmob-customcode-example" target="_blank" class="button">Fork the Custom Code Example on GitHub</a></p>

You can feel free to add your own classes and use this as a template to build off of.


# Building Custom Code from Scratch

If you'd rather build a custom code project from scratch, read on.

## Including the Custom Code SDK in your Project

StackMob provides various ways for you to include the Custom Code SDK in your project.  You may choose according to your preference.

* Maven (recommended)
* sbt (also recommended if you're using Scala)
* Download and manually include the JAR (not recommended)

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

Also, you can see what an example pom.xml file would look like for your project [here](https://gist.github.com/1646301).

**sbt**

```scala
libraryDependencies += "com.stackmob" % "customcode" % "0.4.0" % "provided"
```

**Manual**

We do not recommend that you try to manually include the Custom Code SDK JAR in your project because you will also need 
to include its dependencies. If you must, however, please 
[download the SDK JAR](http://search.maven.org/remotecontent?filepath=com/stackmob/customcode/0.4.0/customcode-0.4.0.jar)
and [the Netty JAR](http://search.maven.org/remotecontent?filepath=org/jboss/netty/netty/3.2.5.Final/netty-3.2.5.Final.jar),
and put both JARs into your CLASSPATH so that you can compile your code. These JARs must not be built into your custom code JAR, however.

## Register your Method

StackMob needs to know where to find your defined methods.  Register them in the `EntryPointExtender` and include it in your JAR.

<span class="tab registermethod" title="Java"/>

**Java**

EntryPointExtender.java:

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

  override def methods: java.util.List[CustomCodeMethod] = {
    Arrays.asList(new HelloWorldExample)
  }
    
}
```

<span class="tab registermethod"/>


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

**Manual**

Again, we recommend against this approach, but if you must:

Create a file called JarManifest with nothing but this line in it: 

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

Once you have your custom methods written, package it as a JAR so that it can be <a href="https://www.stackmob.com/platform/api/customcode/upload">uploaded to StackMob</a>. 
Upon uploading, StackMob will immediately process and roll out the code to your application's sandbox environment. 
To test your new REST API method, use the <a href="https://www.stackmob.com/platform/api/console">console</a>.

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
  Long score = Long.parseLong(request.getParams().get("score"));

  if (username == null || username.isEmpty() || score == null) {
    HashMap<String, String> errParams = new HashMap<String, String>();
    errParams.put("error", "one or both the username or score was empty or null");
    return new ResponseToProcess(HttpURLConnection.HTTP_BAD_REQUEST, errParams); // http 400 - bad request
  }

  // get the datastore service and assemble the query
  DataService dataService = serviceProvider.getDataService();

  // build a query
  List<SMCondition> query = new ArrayList<SMCondition>();
  query.add(new SMEquals("username", new SMString(username)));

  // execute the query
  List<SMObject> result;
  try {
    boolean newUser = false;
    boolean updated = false;

    result = dataService.readObjects("users", query);

    SMObject userObject;

    // user was in the datastore, so check the score and update if necessary
    if (result != null && result.size() == 1) {
      userObject = result.get(0);
    } else {
      Map<String, SMValue> userMap = new HashMap<String, SMValue>();
      userMap.put("username", new SMString(username));
      userMap.put("score", new SMInt(0L);
      newUser = true;
      userObject = new SMObject(userMap);
    }

    SMValue oldScore = userObject.getValue().get("score");

    // if it was a high score, update the datastore
    List<SMUpdate> update = new ArrayList<SMUpdate>();
    if (oldScore == null || ((SMInt)oldScore).getValue() < score) {
      update.add(new SMSet("score", new SMInt(score)));
      updated = true;
    }

    if(newUser) {
      dataService.createObject("users", userObject);
    } else if(updated) {
      dataService.updateObject("users", username, update);
    }

    Map<String, Object> returnMap = new HashMap<String, Object>();
    returnMap.put("updated", updated);
    returnMap.put("newUser", newUser);
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
def execute(request: ProcessedAPIRequest, serviceProvider: SDKServiceProvider): ResponseToProcess = {
  val username = request.getParams.get("username")
  val score = request.getParams.get("score").toLong

  if (username == null || username.isEmpty || score == null) {  // http 400 - bad request
    return new ResponseToProcess(HTTP_BAD_REQUEST, Map("error" -> "one or both the username or score was empty or null").asJava)
  }

  // get the datastore service and assemble the query
  val dataService: DataService = serviceProvider.getDataService
  val query = List[SMCondition](new SMEquals("username", new SMString(username)))

  try {
    //execute the query
    val result = dataService.readObjects("users", query.asJava)

    // check if the user is in the datastore
    val (userObject, newUser) = result match {
      case (userObj: SMObject) :: Nil => (userObj,  false)
      case _ => (new SMObject(Map[String, SMValue[_]]("username" -> new SMString(username)).asJava), true)
    }

    // if it's a new high score, the database needs to be updated
    val updated = userObject.getValue.get("score") match {
      case s: SMInt if s.getValue < score => true
      case _ => false
    }

    if (newUser) {
      dataService.createObject("users", new SMObject((userObject.getValue.asScala + ("score" -> new SMInt(score))).asJava))
    } else if (updated) {
      dataService.updateObject("users", username, Arrays.asList(new SMSet("score", new SMInt(score))))
    }

    new ResponseToProcess(HTTP_OK, Map("updated" -> updated, "newUser" -> newUser, "username" -> username).asJava) // http 200 - ok
  } catch { // http 500 - internal server error
    case e: InvalidSchemaException => new ResponseToProcess(HTTP_INTERNAL_ERROR , Map("error" -> "invalid_schema", "detail" -> e.toString).asJava)
    case e: DatastoreException => new ResponseToProcess(HTTP_INTERNAL_ERROR , Map("error" -> "datastore_exception", "detail" -> e.toString).asJava)
    case e => new ResponseToProcess(HTTP_INTERNAL_ERROR , Map("error" -> "unknown", "detail" -> e.toString).asJava)
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
    return new ResponseToProcess(HTTP_UNAUTHORIZED, Map("error" -> "no user is logged in").asJava)
  }

  new ResponseToProcess(HTTP_OK, Map("currentLogin" -> username).asJava) // http 200 - ok
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
  val users:List[String] = List("JohnDoe")
  val tokensForJohnDoe = pushService.getAllTokensForUsers(users.asJava)

  //send a push notification just to John Doe's iOS device
  val tokensToSendTo:List[TokenAndType] = List(iOSToken)
  val payload: Map[String, String] = Map("badge" -> "1",
    "sound" -> "customsound.wav",
    "alert" -> "Hello from Stackmob!",
    "other" -> "stuff")
  pushService.sendPushToTokens(tokensToSendTo.asJava, payload.asJava)

  //send a push notification to all of John Doe's devices
  pushService.sendPushToUsers(users.asJava, payload.asJava)

  //broadcast a push notification to EVERYONE - use carefully!
  pushService.broadcastPush(payload.asJava)

  //remove the iOS token for John Doe
  pushService.removeToken(iOSToken)

  new ResponseToProcess(HTTP_OK, Map("status" -> "ok").asJava)
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

  new ResponseToProcess(HTTP_OK, Map("status" -> "ok").asJava)
}
```
<span class="tab logging"/>


## Release Notes

<a href="https://github.com/stackmob/stackmob-customcode-sdk/blob/master/RELEASE_NOTES.md">Release notes are available here</a>.

## Javadocs

<a href="http://stackmob.github.com/stackmob-customcode-sdk/0.4.0/apidocs/">Javadocs are available here</a>.


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
