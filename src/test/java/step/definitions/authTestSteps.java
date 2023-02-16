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
import org.json.JSONArray;
import org.junit.Assert;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;


import static org.junit.Assert.assertTrue;


public class authTestSteps {

    private Scenario scenario;
    private Response response;
    private final String BASE_URL = "https://restful-booker.herokuapp.com/";

    @Before

    public void before(Scenario scenarioVal) {
        this.scenario = scenarioVal;
    }
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @Given("Auth method call to {string} with {string} and {string}")
    public void auth_post_call_to_url(String url, String user, String password) throws Exception {
        Map<String, String> authData = new HashMap<>();
        authData.put("username", user);
        authData.put("password", password);

        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().contentType(ContentType.JSON).body(authData);
        scenario.log("Request Send == " + RestAssured.given());
        response = req.when().post(new URI(url));
    }

    @Then("Response Code {string} and {string} is validated")
    public void auth_response_is_validated(String responseMessage, String responseElement) {

        int responseCode = response.then().extract().statusCode();
        response = response.then().extract().response();
        scenario.log("Response Code == " + responseCode);
        scenario.log("Response Received == " + response.asPrettyString());
        Assert.assertEquals(responseMessage, responseCode+"");
        assertTrue(response.getBody().asString().contains(responseElement));
    }

    @Then("Unsuccessful Response Code {string} and response {string} is validated")
    public void unsuccessful_auth_response_is_validated(String responseMessage, String reason) {
        int responseCode = response.then().extract().statusCode();
        response = response.then().extract().response();
        scenario.log("Response Code == " + responseCode);
        scenario.log("Response Received == " + response.asPrettyString());
        Assert.assertEquals(responseMessage, responseCode+"");
        assertTrue(response.getBody().asString().contains(reason));
    }
    @Then("Response  is array total {string}")
    public void responseIsArrayWith(String arg0) {
        response.then().statusCode(200);
        response = response.then().extract().response();
        scenario.log("Response Received == " + response.asPrettyString());
        JSONArray resJson = new JSONArray(response.asString());
        Assert.assertEquals(resJson.length()+"",arg0);


    }

}

