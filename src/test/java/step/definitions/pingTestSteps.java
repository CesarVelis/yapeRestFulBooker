package step.definitions;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.messages.internal.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.junit.Assert;

import java.net.URI;


import static org.junit.Assert.assertTrue;


public class pingTestSteps {

    private Scenario scenario;
    private Response response;
    private final String BASE_URL = "https://restful-booker.herokuapp.com/";

    @Before

    public void before(Scenario scenarioVal) {
        this.scenario = scenarioVal;
    }
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @Given("Ping method call to {string}")
    public void ping_get_call_to_url(String url) throws Exception {

        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().contentType(ContentType.JSON);
        scenario.log("Request Send == " + RestAssured.given().contentType(ContentType.JSON));
        response = req.when().get(new URI(url));
    }

    @Then("Ping response Code {string} and {string} is validated")
    public void ping_response_is_validated(String responseMessage, String responseElement) {

        int responseCode = response.then().extract().statusCode();
        response = response.then().extract().response();
        scenario.log("Response Code == " + responseCode);
        scenario.log("Response Received == " + response.asPrettyString());
        Assert.assertEquals(responseMessage, responseCode+"");
        assertTrue(response.getBody().asString().contains(responseElement));
    }
}

