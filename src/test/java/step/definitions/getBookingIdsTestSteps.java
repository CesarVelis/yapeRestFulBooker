package step.definitions;


import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;


public class getBookingIdsTestSteps {

    private Scenario scenario;
    private Response response;
    private final String BASE_URL = "https://restful-booker.herokuapp.com/";

    @Before

    public void before(Scenario scenarioVal) {
        this.scenario = scenarioVal;
    }

    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @When("GetBookingIds method call to {string} with {string} and {string} and {string} and {string}")
    public void getbookingids_get_call_to_url(String url, String firstname, String lastname,String checkin,String checkout) throws Exception {
        Map<String, String> getBookinIdData = new HashMap<>();
        getBookinIdData.put("firstname", firstname);
        getBookinIdData.put("lastname", lastname);
        getBookinIdData.put("checkin", checkin);
        getBookinIdData.put("checkout", checkout);

        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().contentType(ContentType.JSON).body(getBookinIdData);
        scenario.log("Request Send == " +getBookinIdData);
//        scenario.log("Request Send == " + RestAssured.given().contentType(ContentType.JSON).body(getBookinIdData));
        response = req.when().get(new URI(url));
    }

    @Then("GetBookingIds Response Code {string} and {string} is validated")
    public void getbookingids_response_is_validated(String responseMessage, String responseElement) {

        int responseCode = response.then().extract().statusCode();
        response = response.then().extract().response();
        scenario.log("Response Code == " + responseCode);
//        scenario.log("Response Received == " + response.asPrettyString());
        Assert.assertEquals(responseMessage, responseCode+"");
        assertTrue(response.getBody().asString().contains(responseElement));
    }


    @Given("I add this guess on the GetBookingIds method to be sure the record exist {string}")
    public void iAddThisGuessOnTheGetBookingIdsMethodToBeSureTheRecordExist(String url,String requestBody) throws Exception {

        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().contentType(ContentType.JSON).body(requestBody);
        scenario.log("Request Send == " +requestBody);
        response = req.when().post(new URI(url));
        int responseCode = response.then().extract().statusCode();
        response = response.then().extract().response();
        scenario.log("Response Code == " + responseCode);
        scenario.log("Response Received == " + response.asPrettyString());
        Assert.assertEquals(responseCode, 200);
    }
}



