# Blogius Backend

REST API backend for the Blogius blogging platform, built with [Quarkus 3.31.4](https://quarkus.io/) and Java 25.

## Overview

Blogius Backend provides a JSON REST API for managing blog posts. It is designed to pair with an Angular frontend running on `http://localhost:4200`.

**Tech stack:**
- **Framework:** Quarkus 3.31.4
- **Language:** Java 25
- **Database:** PostgreSQL (via JDBC + Hibernate ORM with Panache)
- **Serialization:** Jackson (via `quarkus-rest-jackson`)
- **Validation:** Hibernate Validator
- **API Docs:** SmallRye OpenAPI + Swagger UI

## API Endpoints

Base path: `/api/posts`

| Method   | Path              | Description             | Response        |
|----------|-------------------|-------------------------|-----------------|
| `GET`    | `/api/posts`      | List all posts          | `200 OK`        |
| `GET`    | `/api/posts/{id}` | Get a post by ID        | `200 OK` / `404`|
| `POST`   | `/api/posts`      | Create a new post       | `201 Created`   |
| `PUT`    | `/api/posts/{id}` | Update an existing post | `200 OK` / `404`|
| `DELETE` | `/api/posts/{id}` | Delete a post           | `204 No Content`/ `404` |

### Post model

```json
{
  "id": 1,
  "title": "My First Post",
  "content": "Post body text...",
  "author": "Jane Doe",
  "createdAt": "2026-02-26T10:00:00",
  "updated_at": "2026-02-26T12:00:00"
}
```

Interactive API documentation is available via Swagger UI at `http://localhost:8080/q/swagger-ui/` when the application is running.

## Prerequisites

- Java 25+
- Maven (or use the included `./mvnw` wrapper)
- PostgreSQL instance

## Configuration

Database and other settings are configured in `src/main/resources/application.properties`:

```properties
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=<your-user>
quarkus.datasource.password=<your-password>
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/<your-db>

quarkus.hibernate-orm.database.generation=drop-and-create

quarkus.http.cors.enabled=true
quarkus.http.cors.origins=http://localhost:4200

quarkus.swagger-ui.always-include=true
```

> **Note:** `drop-and-create` recreates the database schema on every startup. Change to `update` or `none` for persistent data in production.

## Running in dev mode

Dev mode enables live reload and the Quarkus Dev UI at `http://localhost:8080/q/dev/`:

```shell
./mvnw quarkus:dev
```

## Packaging and running

Build a standard JAR:

```shell
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

Build an über-jar (all dependencies bundled):

```shell
./mvnw package -Dquarkus.package.jar.type=uber-jar
java -jar target/*-runner.jar
```

## Native executable

Compile to a native binary with GraalVM:

```shell
./mvnw package -Dnative
./target/blogius-backend-1.0.0-SNAPSHOT-runner
```

Without a local GraalVM installation, build inside a container:

```shell
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

## Docker

Pre-built Dockerfiles for different deployment modes are located in `src/main/docker/`:

| File                        | Description                     |
|-----------------------------|---------------------------------|
| `Dockerfile.jvm`            | JVM mode                        |
| `Dockerfile.legacy-jar`     | Legacy JAR / über-jar mode      |
| `Dockerfile.native`         | Native executable               |
| `Dockerfile.native-micro`   | Native executable (micro image) |

## Running tests

```shell
./mvnw test
```

Integration tests (runs against the packaged application):

```shell
./mvnw verify
```

## Project structure

```
src/main/java/com/blogius/
├── entity/
│   └── Post.java           # JPA entity (Panache active record)
├── resource/
│   └── PostResource.java   # REST endpoints
└── service/
    └── PostService.java    # Business logic
```
