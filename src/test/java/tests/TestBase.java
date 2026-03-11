package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
    @BeforeAll
    public static void openBaseUri(){
        RestAssured.baseURI = "https://selenoid.autotests.cloud";
        RestAssured.basePath = "/wd/hub";
    }
}
