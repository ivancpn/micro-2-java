package com.example.ordini;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@QuarkusTest
class OrdiniResourceTest {

    @Test
    void elencoOrdini() {
        given().when().get("/ordini")
                .then().statusCode(200)
                .body("size()", greaterThanOrEqualTo(2));
    }

    @Test
    void creaEAnnullaOrdine() {
        Integer id = given().contentType(ContentType.JSON)
                .body("{\"prodottoId\":2,\"quantita\":3}")
                .when().post("/ordini")
                .then().statusCode(201)
                .body("stato", equalTo("CREATO"))
                .extract().path("id");

        given().when().post("/ordini/" + id + "/annulla")
                .then().statusCode(200)
                .body("stato", equalTo("ANNULLATO"));
    }

    @Test
    void quantitaNonValida() {
        given().contentType(ContentType.JSON)
                .body("{\"prodottoId\":2,\"quantita\":0}")
                .when().post("/ordini")
                .then().statusCode(400);
    }

    @Test
    void ordineInesistente() {
        given().when().get("/ordini/9999")
                .then().statusCode(404);
    }

    @Test
    void health() {
        given().when().get("/q/health")
                .then().statusCode(200)
                .body("status", equalTo("UP"));
    }
}
