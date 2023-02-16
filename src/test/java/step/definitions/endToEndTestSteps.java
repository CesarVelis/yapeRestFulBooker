package step.definitions;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.messages.internal.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class endToEndTestSteps {
    private Scenario scenario;
    private Response response;
    private final String BASE_URL = "https://restful-booker.herokuapp.com/";

    @Before

    public void before(Scenario scenarioVal) {
        this.scenario = scenarioVal;
    }
    @JsonIgnoreProperties({"hibernateLazyInitializer"})


    @Given("Login Auth method call to {string} with {string} and {string}")
    public void loginAuthMethodCallToWithAnd(String url, String user, String password) throws Exception {

        Map<String, String> authData = new HashMap<>();
        authData.put("username", user);
        authData.put("password", password);

        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().contentType(ContentType.JSON).body(authData);
//        scenario.log("Request Send Login == " + RestAssured.given().get().asPrettyString());
        response = req.when().post(new URI(url));
    }

    @Then("Login Response Code {string} and {string} is validated")
    public void loginResponseCodeAndIsValidated(String responseMessage, String reason) {
        int responseCode = response.then().extract().statusCode();
        response = response.then().extract().response();
        scenario.log("Response Code Login == " + responseCode);
//        scenario.log("Response Received Login == " + response.asPrettyString());
        Assert.assertEquals(responseMessage, responseCode+"");
        assertTrue(response.getBody().asString().contains(reason));
    }

    @Then("using the service {string} validate that the client was not register already, searching by firstname {string} and {string} validating the {string}")
    public void usingTheServiceValidateThatTheClientWasNotRegisterAlreadySearchingByFirstnameAnd(String url, String firstname, String lastname, String responseElement) throws Exception {
        Map<String, String> getBookinIdData = new HashMap<>();
        getBookinIdData.put("firstname", firstname);
        getBookinIdData.put("lastname", lastname);


        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().contentType(ContentType.JSON).params(getBookinIdData);
//        scenario.log("Request Send Find == " + RestAssured.given().get().asPrettyString());
        scenario.log("Request Send Find== " +getBookinIdData);
//        scenario.log("Request Send == " + RestAssured.given().contentType(ContentType.JSON).body(getBookinIdData));
        response = req.when().get(new URI(url));

        int responseCode = response.then().extract().statusCode();
        response = response.then().extract().response();
        scenario.log("Response Code Find == " + responseCode);
        scenario.log("Response Received Find == " + response.asPrettyString());
        scenario.log("IF THESE STEP FAIL IS BECAUSE THE USER IS ALREADY REGISTER");
//        scenario.log("Response Received == " + response.asPrettyString());
//        Assert.assertEquals(responseMessage, responseCode+"");
        assertFalse(response.getBody().asString().contains(responseElement));
    }


    @And("using this data as input, we can create a new user with the service: {string}")
    public void usingThisDataAsInputWeCanCreateANewUserWithTheService(String url,String requestBody) throws Exception{
        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().contentType(ContentType.JSON).body(requestBody);
//        scenario.log("Request Send Find == " + RestAssured.given().get().asPrettyString());
        scenario.log("Request Send Create == " +requestBody);
        response = req.when().post(new URI(url));

    }


    @And("we can validate the data {string} and the id the was given by the system {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void weCanValidateTheDataAndTheIdTheWasGivenByTheSystem(String responseMessage, String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneeds) throws Exception{
        int responseCode = response.then().extract().statusCode();
        response = response.then().extract().response();
        scenario.log("Response Code Created == " + responseCode);
        scenario.log("Response Received Created == " + response.asPrettyString());
        Assert.assertEquals(responseMessage, responseCode + "");
        assertTrue(response.getBody().asString().contains(firstname));
        assertTrue(response.getBody().asString().contains(lastname));
        assertTrue(response.getBody().asString().contains(totalprice));
        assertTrue(response.getBody().asString().contains(depositpaid));
        assertTrue(response.getBody().asString().contains(checkin));
        assertTrue(response.getBody().asString().contains(checkout));
        assertTrue(response.getBody().asString().contains(additionalneeds));
    }


}
