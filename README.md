# Kanga API client
Gets data from Kanga API and does simple aggregation
## How to build, run and use
Project is based on Gradle, so the simplest way is to use gradle wrapper.
### Preconditions
- JDK 21
- JAVA_HOME env. variable set to JDK main directory
### Build
`gradlew clean build bootJar`
In the `./build/libs` directory the `kangamarket-x.x.x.jar` artifact will be created.
### Run
`java -jar kangamarket-x.x.x.jar`

### Use
This client offers 2 methods
- retrieve data from Kanga
- get aggregated report
Http requests are defined in [apicalls.http](src/apicalls.http) to make calls from JetBrains tools.

Here the curl commands to the application endpoints
`curl -X GET --location "http://localhost:8080/api/spread/ranking" \
-H "Content-Type: application/json"`

`curl -X POST --location "http://localhost:8080/api/spread/calculate" \
-H "Authorization: Bearer ABC123"`
