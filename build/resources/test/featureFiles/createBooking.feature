Feature: Validate the behavior of createBooking api service

  Scenario Outline: Validate CreateBooking Services - HappyPath

    Given I want to add a new guest using the Booking service using the following data: "<url>"
    """
    {
        "firstname": "Juanito",
        "lastname": "Perez",
        "totalprice": 444,
        "depositpaid": true,
        "bookingdates": {
            "checkin": "2023-02-15",
            "checkout": "2023-02-28"
        },
        "additionalneeds": "Limousine"
    }
    """

    Then I want to validate a successful code was received: "<responseMessage>" and the following response was delivered: "<firstname>", "<lastname>", "<totalprice>", "<depositpaid>", "<checkin>", "<checkout>", "<additionalneeds>"

    Examples: Create Booking HappyPath Scenario
      | url     | responseMessage | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | booking | 200             | Juanito   | Perez    | 444        | true        | 2023-02-15 | 2023-02-28 | Limousine       |


  Scenario Outline: Create Booking Services - UnhappyPath Error 500

    Given I want to add a new guest using the Booking service using the following data: "<url>"
    """
    {
        "firstname": "Juanito",
        "lastname": "Perez",
        "depositpaid": true,
        "bookingdates": {
            "checkin": "2023-02-15",
            "checkout": "2023-02-28"
        },
        "additionalneeds": "Limousine"
    }
    """

    Then I want to validate a unsuccessful code was received: "<responseMessage>" with response "<description>"

    Examples: Create Booking UnHappyPath Scenario Error 500
      | url     | responseMessage | description            |
      | booking | 500             | Internal Server Error  |


  Scenario Outline: Create Booking Services - UnhappyPath Error 400

    Given I want to add a new guest using the Booking service using the following data: "<url>"
    """
    {
        "lastname": "Perez",
        "totalprice": 777,
        "depositpaid": true,
        "bookingdates": {
            "checkin": "2023-02-15",
            "checkout": "2023-02-28"
        },
        "additionalneeds": "Limousine"
    }
    {firstname=Cesar, additionalneeds=Dinner, bookingdates.checkin=2023-02-15, bookingdates.checkout=2023-02-28, totalprice=444, depositpaid=true, lastname=Velis}
    """

    Then I want to validate a unsuccessful code was received: "<responseMessage>" with response "<description>"

    Examples: Create Booking UnHappyPath Scenario Error 400
      | url     | responseMessage | description  |
      | booking | 400             | Bad Request  |

