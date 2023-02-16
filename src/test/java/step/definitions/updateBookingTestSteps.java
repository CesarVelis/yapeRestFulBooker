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
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class updateBookingTestSteps {
    private Scenario scenario;
    private Response response;
    private Response responseAuth;

    private final String BASE_URL = "https://restful-booker.herokuapp.com/";

    @Before

    public void before(Scenario scenarioVal) {
        this.scenario = scenarioVal;
        Map<String, String> authData = new HashMap<>();
        authData.put("username", "admin");
        authData.put("password", "password123");

        baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().contentType(ContentType.JSON).body(authData);
        scenario.log("Request Send == " + RestAssured.given());
        responseAuth = given().contentType(ContentType.JSON)
                .body(authData)
                .when()
                .post(BASE_URL +"auth");
        responseAuth = responseAuth.then().extract().response();
//        scenario.log("Response Code == " + responseCode);
        scenario.log("Response Received == " + responseAuth.asPrettyString());
        String token = responseAuth.jsonPath().getString("token");
        scenario.log("Response Auth Token == " + token);

    }

    @JsonIgnoreProperties({"hibernateLazyInitializer"})

    @Given("I want to add a new guest using the Booking service using the following data: {string} so we can edited later")
    public void addGuessUsingTheBookingServiceUsingTheFollowingData(String url,String requestBody) throws Exception {


        baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().contentType(ContentType.JSON).body(requestBody);
        scenario.log("Request Send == " +requestBody);
        response = req.when().post(new URI(url));

    }

    @Then("I want to validate a successful code was received: {string}")
    public void createSuccessfulCode(String responseMessage) throws Exception {

        int responseCode = response.then().extract().statusCode();
        response = response.then().extract().response();
        scenario.log("Response Code == " + responseCode);
        scenario.log("Response Received == " + response.asPrettyString());
        Assert.assertEquals(responseMessage, responseCode + "");
        response.then().extract().response().getBody();
        String ServiceResponse = response.asPrettyString();
        scenario.log("ServiceResponse == "+ServiceResponse);


    }


    @Then("as a operator i realize the record was added wrong so i want update {string} that record using the following information")
    public void asAOperatorIRealizeTheRecordWasAddedWrongSoIWantUpdateThatRecordUsingTheFollowingInformation(String url,String requestBody) throws Exception {

        String bookingId = response.jsonPath().getString("bookingid");
        scenario.log("ServiceResponse == "+bookingId);
        String token = responseAuth.jsonPath().getString("token");
        baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().contentType(ContentType.JSON).header("Cookie", "token="+token).body(requestBody);
        scenario.log("Request Send Put == " +requestBody);
        scenario.log("Request Send Put == " + baseURI);
        response = req.when().put(new URI(url+"/"+bookingId));
        scenario.log("URL Send Put == " + baseURI+url+"/"+bookingId);

        response = response.then().extract().response();
        scenario.log("Response Code == " + response);
        scenario.log("Response Update Received == " + response.asPrettyString());
        Assert.assertEquals(200, response.then().extract().statusCode());

    }

    @Then("using the UpdateBooking method I can validate that i receive the final guess data update")
    public void usingTheUpdateBookingMethodICanValidateThatIReceiveTheFinalGuessDataUpdate(String requestBody) throws Exception {

        requestBody.contains(response.asPrettyString());

    }
}
