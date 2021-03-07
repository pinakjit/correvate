# Getting Started

### Spring Boot Project
This is a demo application for correvate interview test. Since the project is estimated to be
take 1.5 hrs i have limited it to just the barebones structure.

The project has just one api which allows you to upload multiplefiles and get a zipped file in response.
Things to consider

* Swagger is configured so api defination is available at http://localhost:8080/v2/api-docs
* API POST service available at http://localhost:8080/uploadMultiFile can be tested with postman file upload
* Spring security is not enabled but can be configured  - usertable, jwttoken or oauth for security
* Code test coverage can be configured using maven jcoco dependency. Presently not configured.




### Docker Image
The following guides illustrate how to run the app as docker image
* Make sure docker is installed in your machine
* Run the following command to pull the docker image "docker pull pinakjit2/correvatefilemanagement-correvate-file-management-app:0.0.1-SNAPSHOT"
* Run the following command to run the service "docker run -p 8000:8000 pinakjit2/cfm-correvate-file-management-app:0.0.1-SNAPSHOT"
* In the browser go to url http://localhost:8080/v2/api-docs to check if the instance has started


### Further Development for high availibility and resilience
* Create a service discovery server (Eureka) and register the server in our app
* Create multiple instance of the service using the docker image
* Build a Spring config server to manage configuration for all the services
* Create a zipkin tracing server and configure to trace all request logs
* Create a yaml file in doctor compose to deploy all the above images as part of one docker run