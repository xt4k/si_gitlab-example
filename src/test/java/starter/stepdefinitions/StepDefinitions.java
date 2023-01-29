package starter.stepdefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import starter.actions.CommonActions;
import starter.assertions.ResponseVerification;
import starter.pojo.Drink;

import java.util.List;

import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static net.serenitybdd.rest.SerenityRest.then;
import static org.hamcrest.Matchers.is;
import static starter.enums.Const.USER_NAME;

public class StepDefinitions {

    @Steps
    public ResponseVerification responseVerification;

    @Steps
    public CommonActions actions;

    /********************************* WHEN *********************************/

    @When("^I am searching for (.*)$")
    public void searchProducts(String item) {
        actions.searchTestProducts(item);
    }

    @When("I do search for product {string}")
    public void searchProduct(String product) {
        actions.searchTestProducts(product);
    }

    @When("I am searching without {string}")
    public void searchWithoutCriteria(String item) {
        actions.searchTestProducts("");
    }

    @When("an authorized user call endpoint {string}")
    public void authorizedUserCall(String endPoint) {
        actions.authorizedCall(endPoint);
    }

    @When("an unauthorized user call {string}")
    public void unauthorizedUserCall(String endPoint) {
        actions.unauthorizedCall(endPoint);
    }

    /********************************* THEN *********************************/

    @Then("get unauthorized error message")
    public void getUnauthorizedErrorMessage() {
        responseVerification.unauthorized();
    }

    @Then("does not see the results")
    public void noResults() {
        actions.returnError();
    }

    @Then("search result is successful")
    public void searchResultIsSuccessful() {
        responseVerification.success();
    }

    @Then("verify the product list should not be empty in Search results")
    public void theProductShouldBeDisplayedInSearchResults() {
        responseVerification.responseShouldNotBeEmptyList();
    }

    @Then("search result return not found error")
    public void notFoundErrorShouldBeDisplayedInSearchResult() {
        responseVerification.notFound();
    }

    @Then("verify not authenticated error should be displayed in search result")
    public void notAuthenticatedErrDisplayedInSearchResult() {
        responseVerification.notAuthenticated();
    }

    @Then("^search result should contain (.*)$")
    public void productShouldBePresentInSearchResults(String product) {
        responseVerification.verifyProductInResponseResult(product);
    }

    @Then("all product in search results contains {string}")
    public void allProductInSearchResultContain(String type) {
        restAssuredThat(response -> {
            List<Drink> drinks;
            try {
                drinks = new ObjectMapper().readValue(response.extract().jsonPath().prettyPrint(), new TypeReference<List<Drink>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            drinks.forEach(a -> a.title.contains(type));

        });
    }

    /********************************* AND *********************************/
    @And("the schema should match to specification defined in {string}")
    public void schemaMatchWithSpecification(String spec) {
        responseVerification.verifyResponseSchema(spec);
    }

    @And("response field {string} has value {string}")
    public void responseFieldHasValue(String field, String fieldValue) {
        then().body(field, is(fieldValue));
    }

    @And("account contains user name")
    public void account_object_contains_user_name() throws JsonProcessingException {
        responseVerification.accountFieldContain(USER_NAME.getValue());
    }

}
