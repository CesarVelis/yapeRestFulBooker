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
public class createBookingTestSteps {
    private Scenario scenario;
    private Response response;
    private final String BASE_URL = "https://restful-booker.herokuapp.com/";

    @Before

    public void before(Scenario scenarioVal) {
        this.scenario = scenarioVal;
    }
    @JsonIgnoreProperties({"hibernateLazyInitializer"})

    @Given("I want to add a new guest using the Booking service using the following data: {string}")
    public void AddANewGuestUsingTheBookingServiceUsingTheFollowingData(String url,String requestBody) throws Exception {

        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().contentType(ContentType.JSON).body(requestBody);
        scenario.log("Request Send == " +requestBody);
        response = req.when().post(new URI(url));

    }

    @Then("I want to validate a successful code was received: {string} and the following response was delivered: {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void iWantToValidateASuccessfulCodeWasReceivedAndTheFollowingResponseWasDelivered(String responseMessage, String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneeds) throws Exception {

        int responseCode = response.then().extract().statusCode();
        response = response.then().extract().response();
        scenario.log("Response Code == " + responseCode);
        scenario.log("Response Received == " + response.asPrettyString());
        Assert.assertEquals(responseMessage, responseCode + "");
        assertTrue(response.getBody().asString().contains(firstname));
        assertTrue(response.getBody().asString().contains(lastname));
        assertTrue(response.getBody().asString().contains(totalprice));
        assertTrue(response.getBody().asString().contains(depositpaid));
        assertTrue(response.getBody().asString().contains(checkin));
        assertTrue(response.getBody().asString().contains(checkout));
        assertTrue(response.getBody().asString().contains(additionalneeds));
    }


    @Then("I want to validate a unsuccessful code was received: {string} with response {string}")
    public void iWantToValidateAUnsuccessfulCodeWasReceivedWithResponse(String responseMessage, String errorDescription) {
        int responseCode = response.then().extract().statusCode();
        response = response.then().extract().response();
        scenario.log("Response Code == " + responseCode);
        scenario.log("Response Received == " + response.asPrettyString());
        Assert.assertEquals(responseMessage, responseCode + "");
        assertTrue(response.getBody().asString().contains(errorDescription));

    }

}
