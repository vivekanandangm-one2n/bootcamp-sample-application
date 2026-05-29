VERSION := latest

test:
	./mvnw test

build_docker:
	./mvnw package
	docker build -f src/main/docker/Dockerfile.jvm -t student-mgmt:$(VERSION) .

build_docker_native:
	./mvnw package -Dnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true
	docker build -f src/main/docker/Dockerfile.native-micro -t student-mgmt:$(VERSION) .