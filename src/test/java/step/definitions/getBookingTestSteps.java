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


public class getBookingTestSteps {

    private Scenario scenario;
    private Response response;


    private final String BASE_URL = "https://restful-booker.herokuapp.com/";

    @Before

    public void before(Scenario scenarioVal) {
        this.scenario = scenarioVal;

    }

    @JsonIgnoreProperties({"hibernateLazyInitializer"})

    @Given("Testing GetBooking method I add this guess on the GetBookingIds method to be sure the record exist {string}")
    public void testingGetBookingMethodIAddThisGuessOnTheGetBookingIdsMethodToBeSureTheRecordExist(String url,String requestBody) throws Exception {

        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().contentType(ContentType.JSON).body(requestBody);
        scenario.log("Request Send Create == " +requestBody);
        response = req.when().post(new URI(url));
        int responseCode = response.then().extract().statusCode();
        response = response.then().extract().response();
        scenario.log("GetBookingMethod Response Code Create== " + responseCode);
        scenario.log("GetBookingMethod Response Received Create == " + response.asPrettyString());
        Assert.assertEquals(responseCode, 200);
        String id = response.jsonPath().getString("bookingid");
        scenario.log("GetBookingMethod BookingId == " + id);
    }
    @Given("GetBooking method call to {string} with id")
    public void getBooking_get_call_to_url(String url) throws Exception {

        String id = response.jsonPath().getString("bookingid");
        scenario.log("GetBookingMethod BookingId == " + id);
        String editedBaseUrl =  BASE_URL + url + "/" + id;
        scenario.log("GetBookingMethod Full URL == " + editedBaseUrl);
        RestAssured.baseURI = editedBaseUrl;
        RequestSpecification req = RestAssured.given().contentType(ContentType.JSON);
        scenario.log("GetBookingMethod Request Send == " + editedBaseUrl);

        response = req.when().get();
    }

    @Then("GetBooking Response Code {string} and the following fields need to be validated: {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void getBooking_response_is_validated(String responseMessage, String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneeds) throws Exception {

        int responseCode = response.then().extract().statusCode();
        response = response.then().extract().response();
        scenario.log("GetBookingMethod Response Code == " + responseCode);
        scenario.log("GetBookingMethod Response Received == " + response.asPrettyString());
        Assert.assertEquals(responseMessage, responseCode + "");

//        Commenting the following lines because the list have dynamics value so no all the time retrieve the same result with the same id
        assertTrue(response.getBody().asString().contains(firstname));
        assertTrue(response.getBody().asString().contains(lastname));
        assertTrue(response.getBody().asString().contains(totalprice));
        assertTrue(response.getBody().asString().contains(depositpaid));
        assertTrue(response.getBody().asString().contains(checkin));
        assertTrue(response.getBody().asString().contains(checkout));
        assertTrue(response.getBody().asString().contains(additionalneeds));
    }


}



