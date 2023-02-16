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

public class deleteTestSteps {
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
//        scenario.log("Request Send == " + RestAssured.given());
        responseAuth = given().contentType(ContentType.JSON)
                .body(authData)
                .when()
                .post(BASE_URL +"auth");
        responseAuth = responseAuth.then().extract().response();
//        scenario.log("Response Code == " + responseCode);
//        scenario.log("Response Received == " + responseAuth.asPrettyString());
        String token = responseAuth.jsonPath().getString("token");
//        scenario.log("Response Auth Token == " + token);


    }
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @Given("using DeleteBooking service I want to add a new guest using the Booking service using the following data: {string} so we can edited later")
    public void usingDeleteBookingServiceIWantToAddANewGuestUsingTheBookingServiceUsingTheFollowingDataSoWeCanEditedLater(String url,String requestBody) throws Exception {

        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().contentType(ContentType.JSON).body(requestBody);
        scenario.log("Request Send == " +requestBody);
        response = req.when().post(new URI(url));

    }

    @Then("using DeleteBooking service I want to validate a successful code was received: {string}")
    public void usingDeleteBookingServiceIWantToValidateASuccessfulCodeWasReceived(String responseMessage) throws Exception {

        int responseCode = response.then().extract().statusCode();
        response = response.then().extract().response();
        scenario.log("Response Code == " + responseCode);
        scenario.log("Response Received == " + response.asPrettyString());
        Assert.assertEquals(responseMessage, responseCode + "");
        response.then().extract().response().getBody();
        String ServiceResponse = response.asPrettyString();
        scenario.log("ServiceResponse == "+ServiceResponse);


    }

    @Then("I want to delete that guess from our records {string}")
    public void iWantToDeleteThatGuessFromOurRecords(String url) throws Exception {

        String bookingId = response.jsonPath().getString("bookingid");
        scenario.log("BookingId of create guess step == "+bookingId);
        String token = responseAuth.jsonPath().getString("token");
        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().contentType(ContentType.JSON).header("Cookie", "token="+token);
        scenario.log("Request Send PATCH == " + RestAssured.baseURI);
        response = req.when().delete(new URI(url+"/"+bookingId));
        scenario.log("URL Send PATCH == " + RestAssured.baseURI+url+"/"+bookingId);

        response = response.then().extract().response();
        scenario.log("Response Code PATCH == " + response);
        scenario.log("Response Update PATCH Received == " + response.asPrettyString());


    }
    @Then("I can validate that i receive the final a successful message code {string}")
    public void iCanValidateThatIReceiveTheFinalASuccessfulMessageCode(String arg0) {

        Assert.assertEquals(201, response.then().extract().statusCode());
    }


}
