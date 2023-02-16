Feature: Validate the behavior of DeleteBooking api service

  Scenario Outline: Validate DeleteBooking Services - HappyPath

    Given using DeleteBooking service I want to add a new guest using the Booking service using the following data: "<url>" so we can edited later
    """
    {
        "firstname": "Jhon",
        "lastname": "Lenon",
        "totalprice": 444,
        "depositpaid": true,
        "bookingdates": {
            "checkin": "2023-02-15",
            "checkout": "2023-02-28"
        },
        "additionalneeds": "Limousine"
    }
    """

    Then using DeleteBooking service I want to validate a successful code was received: "<responseMessage>"

    Then I want to delete that guess from our records "<url>"

    Then I can validate that i receive the final a successful message code "<responseMessageDelete>"

    Examples: Create Booking HappyPath Scenario
      | url     | responseMessage | responseMessageDelete |
      | booking | 200             |  201                  |
