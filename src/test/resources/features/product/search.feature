Feature: Product search

### Please use endpoint GET https://waarkoop-server.herokuapp.com/api/v1/search/test/{product} for getting the products.
### Available products: "apple", "mango", "tofu", "water"
### Prepare Positive and negative scenarios
  @positive
  Scenario Outline: Search test with available product
    When I am searching for <product>
    Then search result is successful
    And search result should contain <product>
    And the schema should match to specification defined in "product.json"
    Examples:
      | product |
      | apple   |
      | mango   |
      | tofu    |
      | water   |

  @negative
  Scenario Outline: Search unavailable products
    When I am searching for <product>
    Then search result return not found error
    Examples:
      | product |
      | car     |
      | coffee  |

  @negative
  Scenario: Search test without product
    When I am searching without "product"
    Then verify not authenticated error should be displayed in search result

  @positive
  Scenario: Search for product apple
    When I do search for product "apple"
    Then search result is successful

  @positive
  Scenario: Search for product mango
    When I do search for product "mango"
    Then search result is successful
    Then all product in search results contains "mango"