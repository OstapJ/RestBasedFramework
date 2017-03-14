@service
Feature: Verify API calls related to Dashboard functionality

  Scenario: Get dashboard
    When Get Dashboard:
      | projectName | dashboardId              |
      | crt-odc     | 585ceea03cdea20008436b6c |
    Then Dashboard response should contain:
      | owner             | isShared | name  |
      | ievgen_ostapenko | true     | Smoke |


