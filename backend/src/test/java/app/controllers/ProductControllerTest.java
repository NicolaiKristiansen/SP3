package app.controllers;

import app.config.ApplicationConfig;
import app.routes.Routes;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {
    private static int createdProductId;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:7070";
        RestAssured.basePath = "/api/products";
        ApplicationConfig.
                getInstance()
                .initiateServer()
                .setRoute(new Routes().getRoutes())
                .startServer(7007);
    }

    @Test
    @Order(1)
    void create() {
        String productJson =
        """
        {
            "name": "Phone",
            "price": "50.2",
            "category": "ELECTRONICS"
        }
        """;

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(productJson)
                        .when()
                        .post()
                        .then()
                        .contentType(ContentType.JSON)
                        .statusCode(200)
                        .body("productId", equalTo(1))
                        .extract().response();

        createdProductId = response.path("productId");
    }

    @Test
    @Order(2)
    void findById() {
        given()
                .pathParam("id", createdProductId)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .body("productId", equalTo(createdProductId));
    }

    @Test
    @Order(3)
    void update() {
        String updatedProductJson = """
        {
            "name": "Updated Phone",
            "price": "99.9",
            "category": "ELECTRONICS"
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", createdProductId)
                .body(updatedProductJson)
                .when()
                .put("/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(4)
    void delete() {
        given()
                .pathParam("id", createdProductId)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200)
                .body(containsString("deleted"));
    }
}