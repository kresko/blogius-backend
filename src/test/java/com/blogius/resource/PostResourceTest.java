package com.blogius.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class PostResourceTest {

    @Test
    void getAllPosts_returnsEmptyListOrPosts() {
        given()
            .when().get("/api/posts")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("$", isA(java.util.List.class));
    }

    @Test
    void createPost_returnsCreated() {
        given()
            .contentType(ContentType.JSON)
            .body("""
                {
                  "title": "Test Post",
                  "content": "Some content",
                  "author": "Alice"
                }
                """)
            .when().post("/api/posts")
            .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("title", equalTo("Test Post"))
            .body("content", equalTo("Some content"))
            .body("author", equalTo("Alice"))
            .body("createdAt", notNullValue());
    }

    @Test
    void createPost_missingTitle_returnsBadRequest() {
        given()
            .contentType(ContentType.JSON)
            .body("""
                {
                  "content": "No title here",
                  "author": "Bob"
                }
                """)
            .when().post("/api/posts")
            .then()
            .statusCode(400);
    }

    @Test
    void getPost_existingId_returnsPost() {
        Long id = given()
            .contentType(ContentType.JSON)
            .body("""
                {
                  "title": "Fetch Me",
                  "content": "Content",
                  "author": "Charlie"
                }
                """)
            .when().post("/api/posts")
            .then()
            .statusCode(201)
            .extract().jsonPath().getLong("id");

        given()
            .when().get("/api/posts/" + id)
            .then()
            .statusCode(200)
            .body("id", equalTo(id.intValue()))
            .body("title", equalTo("Fetch Me"));
    }

    @Test
    void getPost_nonExistingId_returnsNotFound() {
        given()
            .when().get("/api/posts/999999")
            .then()
            .statusCode(404);
    }

    @Test
    void updatePost_existingId_returnsUpdated() {
        Long id = given()
            .contentType(ContentType.JSON)
            .body("""
                {
                  "title": "Original Title",
                  "content": "Original content",
                  "author": "Dave"
                }
                """)
            .when().post("/api/posts")
            .then()
            .statusCode(201)
            .extract().jsonPath().getLong("id");

        given()
            .contentType(ContentType.JSON)
            .body("""
                {
                  "title": "Updated Title",
                  "content": "Updated content",
                  "author": "Dave"
                }
                """)
            .when().put("/api/posts/" + id)
            .then()
            .statusCode(200)
            .body("title", equalTo("Updated Title"))
            .body("content", equalTo("Updated content"));
    }

    @Test
    void updatePost_nonExistingId_returnsNotFound() {
        given()
            .contentType(ContentType.JSON)
            .body("""
                {
                  "title": "Doesn't matter",
                  "content": "Doesn't matter",
                  "author": "Eve"
                }
                """)
            .when().put("/api/posts/999999")
            .then()
            .statusCode(404);
    }

    @Test
    void deletePost_existingId_returnsNoContent() {
        Long id = given()
            .contentType(ContentType.JSON)
            .body("""
                {
                  "title": "To Be Deleted",
                  "content": "Bye",
                  "author": "Frank"
                }
                """)
            .when().post("/api/posts")
            .then()
            .statusCode(201)
            .extract().jsonPath().getLong("id");

        given()
            .when().delete("/api/posts/" + id)
            .then()
            .statusCode(204);

        given()
            .when().get("/api/posts/" + id)
            .then()
            .statusCode(404);
    }

    @Test
    void deletePost_nonExistingId_returnsNotFound() {
        given()
            .when().delete("/api/posts/999999")
            .then()
            .statusCode(404);
    }
}
