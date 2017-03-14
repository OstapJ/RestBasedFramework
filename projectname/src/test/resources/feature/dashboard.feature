@service
Feature: Verify API calls related to Dashboard functionality

  Scenario: Get dashboard
    When Get Dashboard:
      | projectName | dashboardId              |
      | crt-odc     | 585ceea03cdea20008436b6c |
    Then Dashboard response should contain:
      | owner             | isShared | name  |
      | ievgen_ostapenko | true     | Smoke |

  Scenario: Get dashboards
    When Get Dashboards:
      | projectName | dashboardId              |
      | crt-odc     | 585ceea03cdea20008436b6c |
    Then Dashboard responses should contain:
      | owner            | isShared | name         |
      | ievgen_ostapenko | true     | Smoke        |
      | ievgen_ostapenko | true     | Single Story |
      | ievgen_ostapenko | true     | REGRESSION   |

  Scenario: Create dashboard
    When Post Dashboard:
      | projectName               | name     | share |
      | ievgen_ostapenko_personal | RANDOM_6 | false |
    When Get Dashboard:
      | projectName               | dashboardId        |
      | ievgen_ostapenko_personal | GLOBAL_dashboardId |
    Then Dashboard response should contain:
      | owner            | isShared | name                 |
      | ievgen_ostapenko | false    | GLOBAL_dashboardName |
    When Delete Dashboard:
      | projectName               | dashboardId        |
      | ievgen_ostapenko_personal | GLOBAL_dashboardId |