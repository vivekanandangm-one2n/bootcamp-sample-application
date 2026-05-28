# 27/05/2026

- Implemented flyway to handle database migrations
- Added logging to log incoming request and respective status code of the response
    - Configured MDC to log a request ID to match request with response
- Working on test cases to test the endpoints using RESTAssured and Hamcrest along with Quarkus
  testing

# 28/05/2026

- Implemented test cases for all the operations on the Student resource
- Created a makefile to ease building docker images
- Leveraged Quarkus's support for build native applications through GraavlVM.
- Containerized the native image. Now we have a native application and since it does not need the
  JVM to run the image, the final size of the docker image is much smaller compared to the JVM one.
  It also has a faster startup time.