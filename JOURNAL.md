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

# 29/05/2026

- Setting up a local development environment using docker and make
- Setting up PgAdmin to enable easier local development
- The DB migrations are currently handled during application startup. Looking into solutions on how
  to move the migrations to be run before application startup

# 01/06/2026

- Integrated flyway maven plugin to handle migrations via CLI
- Discussed and got a few feedbacks

# 02/06/2026

- Changed `.properties` to `.yaml` file
- Added OpenAPI dependency that generates a OpenAPI spec and provided Swagger UI in dev mode
- Working on setting up integration tests with testcontainers