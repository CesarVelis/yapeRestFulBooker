Feature: Validate E2E HappyPath

  Scenario Outline: End to End HappyPath

    Given Login Auth method call to "<url>" with "<user>" and "<password>"

    Then Login Response Code "<responseMessage>" and "<responseElement>" is validated

    Then using the service "<find>" validate that the client was not register already, searching by firstname "<firstname>" and "<lastname>" validating the "<findResponseElement>"

    And using this data as input, we can create a new user with the service: "<newGuess>"
    """
    {
        "firstname": "Pedrito",
        "lastname": "Ramirez",
        "totalprice": 777,
        "depositpaid": true,
        "bookingdates": {
            "checkin": "2023-02-15",
            "checkout": "2023-02-28"
        },
        "additionalneeds": "Limousine"
    }
    """

    And we can validate the data "<responseMessage>" and the id the was given by the system "<firstname>", "<lastname>", "<totalprice>", "<depositpaid>", "<checkin>", "<checkout>", "<additionalneeds>"


    Examples: HappyPath End2End Scenario
      | url  | responseMessage | user  | password    | responseElement | find    | findResponseElement | firstname | lastname | checkin    | checkout   |  totalprice | depositpaid | additionalneeds | newGuess |
      | auth | 200             | admin | password123 | token           | booking |  bookingid          | Pedrito   | Ramirez  | 2023-02-15 | 2023-02-28 |  777        | true        |  Limousine      | booking  |