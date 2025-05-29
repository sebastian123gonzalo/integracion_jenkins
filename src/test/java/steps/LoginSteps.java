package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class LoginSteps {
    Response response;

    @Given("the service is available")
    public void the_service_is_available() {
        baseURI="http://localhost:8082/api";
    }

    @When("the client sends username {string} and password {string}")
    public void the_client_sends_username_and_password(String username, String password) {
        String jsonBody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
        response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post("/login");
    }
    @Then("the response should have status code {int}")
    public void the_response_should_have_status_code(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @And("the body should contain {string}")
    public void the_body_should_contain(String expectedText) {
        // Verifica si es JSON o texto plano
        if(response.getContentType().contains("json")) {
            // Para el caso del token en el primer escenario
            if(expectedText.equals("token")) {
                assertTrue(response.getBody().jsonPath().get("token") != null);
            }
            // Para otros casos en JSON
            else {
                assertTrue(response.getBody().asString().contains(expectedText));
            }
        } else {
            // Para respuestas de texto plano
            assertTrue(response.getBody().asString().contains(expectedText));
        }
    }

    @And("the response body should contain key {string}")
    public void verifyBodyContainsKey(String key) {
        assertTrue(response.getBody().jsonPath().get(key) != null);
    }

    @And("the response body's {string} should be {string}")
    public void verifyBodyValue(String key, String expectedValue) {
        assertEquals(expectedValue, response.getBody().jsonPath().getString(key));
    }
}
