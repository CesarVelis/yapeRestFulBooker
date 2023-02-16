Feature: Validate the behavior of AUTH api service

  Scenario Outline: Auth Services HappyPath

    Given Auth method call to "<url>" with "<user>" and "<password>"
    Then Response Code "<responseMessage>" and "<responseElement>" is validated

    Examples: HappyPath Scenario
      | url  | responseMessage | user  | password    | responseElement |
      | auth | 200             | admin | password123 | token           |


  Scenario Outline: Auth Services UnHappyPath

    Given Auth method call to "<url>" with "<user>" and "<password>"
    Then Unsuccessful Response Code "<responseMessage>" and response "<reason>" is validated

    Examples: UnHappyPath Scenario - Wrong Password
      | url  | responseMessage | user  | password | reason          |
      | auth | 200             | admin | password | Bad credentials |

    Examples: UnHappyPath Scenario - Wrong User
      | url  | responseMessage | user | password    | reason          |
      | auth | 200             | user | password123 | Bad credentials |




