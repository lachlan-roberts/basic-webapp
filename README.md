# Basic Webapp

To start webapp run in terminal
```
mvn jetty:run
```

You can use this to play around with the code in `src/main` and try out different things in the servlet API.

You will be able to view the webapp by accessing http://localhost:8080 in the browser.

## Running the Tests

AppEngine uses an embedded Jetty usage. 
So look at the code in `org/example/test/MyTest.java` for an example of how embedded Jetty is used.

You will need to run `mvn clean install -DskipTests` to build the war file at `target/basic-webapp.war` which is used by the test.

## Prerequisites

You will need to have installed JDK-17 or above, and you will need to have installed maven.
These will need to be available on your path, and you will need to have an environment variable set for `JAVA_HOME`.
