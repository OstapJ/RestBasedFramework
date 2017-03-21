@service
Feature: Verify API calls related to Widget Controller functionality

  Scenario: Get all widget names
    When Get Widget names:
      | projectName |
      | crt-odc     |
    Then Widget names response should contain:
      | names                               |
      | LATEST SINGLE STORY LAUNCH          |
      | LATEST REGRESSION                   |
      | OVERALL AMOUNT OF TESTS             |
      | REGRESSION DURATION                 |
      | PASSED TO FAILED                    |
      | FAILED TEST CASES TREND CHART       |
      | LATEST SMOKE LAUNCH                 |
      | SMOKE DURATION                      |
      | Launch statistics trend chart       |
      | DIFFERENT LAUNCHES COMPARISON CHART |
      | Unique bugs table                   |
      | Unique bugs                         |

  Scenario: Get widget
    When Get Widget:
      | projectName | widgetId                 |
      | crt-odc     | 589b1351adbe1d0006bb7b17 |
    Then Widget response should contain:
      | owner               | isShared | name       |
      | yaroslav_shevchenko | true     | Onboarding |

