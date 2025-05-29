Feature: Authentication
  Scenario: Successful login
    Given the service is available
    When the client sends username "admin" and password "1234"
    Then the response should have status code 200
    And the body should contain "token"

  Scenario: Failed login with invalid credentials
    Given the service is available
    When the client sends username "invalid_user" and password "wrong_password"
    Then the response should have status code 401
    And the response body should contain key "detail"
    And the response body's "detail" should be "Credenciales incorrectas"