@service
Feature: Verify API calls related to User functionality

  Scenario: Get user information
    When Get User Information
    Then User information response should contain:
      | userId           | email                     | fullName         | accountType | userRole | defaultProject            |
      | ievgen_ostapenko | ievgen_ostapenko@epam.com | Ievgen Ostapenko | UPSA        | USER     | ievgen_ostapenko_personal |

  Scenario: Get user information by Login
    When Get User Information:
      | userId           |
      | ievgen_ostapenko |
    Then User information response should contain:
      | userId           | email                     | fullName         | accountType | userRole | defaultProject            |
      | ievgen_ostapenko | ievgen_ostapenko@epam.com | Ievgen Ostapenko | UPSA        | USER     | ievgen_ostapenko_personal |