Feature: Validate the behavior of PING api service

  Scenario Outline: Ping Services HappyPath

    Given Ping method call to "<url>"
    Then Ping response Code "<responseMessage>" and "<responseElement>" is validated

    Examples: HappyPath Scenario
      | url  | responseMessage | responseElement |
      | ping | 201             | Created         |