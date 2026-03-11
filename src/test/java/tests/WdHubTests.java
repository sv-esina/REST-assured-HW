package tests;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class WdHubTests extends TestBase{

    @Test
    @DisplayName("Проверка респонса без авторизации")
    public void unauthorizedUserStatusTest() {
        given()
                .log().all()
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(401)
                .body(containsString("401 Authorization Required"));
    }

    @Test
    @DisplayName("Авторизация с некорректным пользователем")
    public void enterIncorrectAuthUserStatusTest() {
        given()
                .log().all()
                .auth().basic("user2", "1234")
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(401)
                .body(containsString("401 Authorization Required"));
    }

    @Test
    @DisplayName("Авторизация с некорректным паролем")
    public void enterIncorrectAuthPassStatusTest() {
        given()
                .log().all()
                .auth().basic("user1", "12345")
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(401)
                .body(containsString("401 Authorization Required"));
    }

    @Test
    @DisplayName("Проверка схемы респонса status json")
    public void checkResponseStatusSchemaTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("wd_hub_status_schema.json"));
    }

    @Test
    @DisplayName("Проверяем наличие объекта value")
    public void checkRequiredValueObjectTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("", hasKey("value"));
    }

    @Test
    @DisplayName("Проверяем наличие ключей в объекте value")
    public void checkRequiredKeysInValueObjectTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("value", hasKey("message"))
                .body("value", hasKey("ready"));
    }

    @Test
    @DisplayName("Проверяем значение ключа message")
    public void checkMessageKeyValueTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("value.message", Matchers.containsString("Selenoid 1.11.3 built at"));
    }

    @Test
    @DisplayName("Проверяем значение ключа ready")
    public void checkReadyKeyValueTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("value.ready", is(true));
    }


}
