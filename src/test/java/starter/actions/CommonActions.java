package starter.actions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.Header;
import io.restassured.response.Response;
import starter.common.RequestSpecification;

import static net.serenitybdd.rest.SerenityRest.given;
import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.hamcrest.Matchers.equalTo;
import static starter.enums.Const.*;

public class CommonActions {
    private Header header= new Header(ACCEPT_HEADER.getField(), ACCEPT_HEADER.getValue());
    private String apiVersionTest="v1/search/test";

    @When("Get response from search product {string}")
    public Response searchTestProducts(String product) {
        return given()
                .log()
                .uri()
                .spec(RequestSpecification.searchReqSpec())
                .pathParam("product", product)
                .get(apiVersionTest+"/{product}");
    }

    @Then("Return error result")
    public void returnError() {
        restAssuredThat(response -> response.body(DETAIL_ERROR.getField(), equalTo(true)));
    }

    @When("Authorized user call {string}")
    public void authorizedCall(String endPoint) {
        given()
                .header(header)
                .header(AUTH_TOKEN.getField(), AUTH_TOKEN.getValue())
                .get(endPoint);
    }

    @When("UnAuthorized user call {string}")
    public void unauthorizedCall(String endPoint) {
        given()
                .header(header)
                .get(endPoint);
    }
}
