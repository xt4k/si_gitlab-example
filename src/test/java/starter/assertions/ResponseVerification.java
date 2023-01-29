package starter.assertions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import net.serenitybdd.rest.SerenityRest;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import starter.pojo.Account;
import starter.pojo.Drink;

import java.util.HashMap;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static starter.enums.Const.DETAIL_ERROR;
import static starter.enums.Const.UNAUTHORIZED;

public class ResponseVerification {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseVerification.class);

    @Then("Verify that API response is {int}")
    private void responseCodeIs(int responseCode) {
        then().statusCode(responseCode);
    }

    @Then("Verify that response list isn't an empty list")
    public void responseShouldNotBeEmptyList() {
        List<HashMap<String, Object>> res = lastResponse().getBody().jsonPath().getList("$");
        LOGGER.info("Response list size is {}", res.size());
        assertThat("Response list size is 0", res.size() == 0);
    }

    @And("Verify response schema with {string}")
    public void verifyResponseSchema(String schemaJson) {
        then().body(matchesJsonSchemaInClasspath("schema/" + schemaJson));
    }

    @Then("response with code 200 (Successful)")
    public void success() {
        responseCodeIs(200);
    }

    @Then("Response error with code 404 (Not Found)")
    public void notFound() {
        then().statusCode(404)
                .body("detail.error", is(true));
    }

    @Then("Response error with code 401 (Not Authorized)")
    public void notAuthenticated() {
        then().statusCode(401).body("detail", is("Not authenticated"));
    }

    @Then("Verify the product in response result")
    public void verifyProductInResponseResult(String product) {
        List<HashMap<String, Object>> products = lastResponse().jsonPath().getList("$");
        Assertions.assertThat(products).anyMatch(e -> e.get("title").toString().contains(product));
    }

    @And("JSON object contains {string}")
    public void accountFieldContain(String value) throws JsonProcessingException {
        Account account = new ObjectMapper().readValue(then().extract().jsonPath().prettyPrint(), new TypeReference<Account>() {});
        AssertionsForClassTypes.assertThat(account.getName().contains(value));
    }

    @Then("Error message for unauthorized access")
    public void unauthorized() {
        then().statusCode(401).body(UNAUTHORIZED.getField(), is(UNAUTHORIZED.getValue()));
    }

    @Then("Each product in search results contains {string}")
    public void allProductTitlesContains(String type) throws JsonProcessingException {
        List<Drink> drinks = new ObjectMapper().readValue( then().extract().jsonPath().prettyPrint(), new TypeReference<List<Drink>>() {});
        drinks.forEach(a -> a.title.contains(type));
    }

    @Then("Response field {string} has value {string}")
    public void fieldHasValue(String field,String value) {
        then().body(field, is(value));
    }

    @Then("Return error result")
    public void returnError() {
        then().body(DETAIL_ERROR.getField(), equalTo(true));
    }

}
