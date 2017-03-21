@service
Feature: Verify published video content on ElenTube site

  @TestCase-2001
  Scenario Outline: : Verify that video section has certain tags
    Given I navigate to Main page
    Then I should see the video with the following values:
      | title   | tag   |
      | <title> | <tag> |
    Examples:
      | title                 | tag  |
      | Emma Watson IFB Tease | Emma |

  @TestCase-2002
  Scenario: Verify video search by tags
    Given Get Facebook
    Then Facebook Status response should contain:
      | current.health   | current.subject | push.status | push.id |
      | ievgen_ostapenko | true            | Smoke       | Smoke   |
    Given I navigate to Main page
    When I search Video with the following values:
      | tag   |
      | Ellen |
    Then I should see the video with the following values:
      | title              | tag   |
      | Video 3 With 1 tag | Ellen |

