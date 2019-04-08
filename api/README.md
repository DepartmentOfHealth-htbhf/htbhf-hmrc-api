# htbhf-hmrc-api

Rest API (implemented with Spring Boot) that interacts with the real HMRC API.
Also checks a database to see if we have details of the NINO in the request, 
using values from the db if it matches and not calling HMRC.

 ### building and running.
run `./gradlew clean build`

 Running locally:
run `./gradlew bootRun` - either from the parent directory or from the `api` directory.
