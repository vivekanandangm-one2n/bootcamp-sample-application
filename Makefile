VERSION := latest

# ignore errors and continue with next command
clean:
	-docker stop bootcamp-pg
	-docker rm bootcamp-pg
	-docker stop pgadmin-container
	-docker rm pgadmin-container
	-docker network rm db-network

# ignore errors and continue with next command
db_up:
	-docker network create db-network
	-docker pull postgres:18.4
	-docker run --name bootcamp-pg -e POSTGRES_PASSWORD=bootcamp -p 5432:5432 --network db-network \
				-e POSTGRES_DB=bootcamp \
				-d postgres:18.4
	-docker pull dpage/pgadmin4:9.15.0
	-docker run --name pgadmin-container -p 5050:80 -e PGADMIN_DEFAULT_EMAIL=bootcamp@one2n.in \
    			-e PGADMIN_DEFAULT_PASSWORD=bootcamp  \
    			-v "./pg-admin/servers.json:/pgadmin4/servers.json" \
    			--network db-network \
    			-d dpage/pgadmin4:9.15.0

dev:
	DB_PASSWORD=bootcamp DB_URL=jdbc:postgresql://localhost:5432/bootcamp DB_USER_NAME=postgres ./mvnw quarkus:dev

test:
	./mvnw clean test

migrate:
	./mvnw -Dflyway.user=postgres -Dflyway.password=bootcamp \
			-Dflyway.url=jdbc:postgresql://localhost:5432/bootcamp flyway:migrate

build_docker:
	./mvnw package
	docker build -f src/main/docker/Dockerfile.jvm -t student-mgmt:$(VERSION) .

build_docker_native:
	./mvnw package -Dnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true
	docker build -f src/main/docker/Dockerfile.native-micro -t student-mgmt:$(VERSION) .

run_docker_local:
	docker run --name local-student-mgmt \
				-e DB_PASSWORD=bootcamp -e DB_URL=jdbc:postgresql://bootcamp-pg:5432/bootcamp \
				-e DB_USER_NAME=postgres -p 8081:8080 --network db-network student-mgmt:$(VERSION)