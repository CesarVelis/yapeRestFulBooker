Feature: Validate the behavior of UpdateBooking api service

  Scenario Outline: Validate UpdateBooking Services - HappyPath

    Given I want to add a new guest using the Booking service using the following data: "<url>" so we can edited later
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

    Then I want to validate a successful code was received: "<responseMessage>"

    Then as a operator i realize the record was added wrong so i want update "<url>" that record using the following information
    """
    {
    "firstname" : "Paul",
    "lastname" : "McCartney",
    "totalprice" : 111,
    "depositpaid" : true,
    "bookingdates" : {
        "checkin" : "2018-01-01",
        "checkout" : "2019-01-01"
    },
    "additionalneeds" : "Breakfast"
    }
    """

    Then using the UpdateBooking method I can validate that i receive the final guess data update
    """
{
    "firstname" : "Paul1",
    "lastname" : "McCartney",
    "totalprice" : 111,
    "depositpaid" : true,
    "bookingdates" : {
        "checkin" : "2018-01-01",
        "checkout" : "2019-01-01"
    },
    "additionalneeds" : "Breakfast"
    }
    """

    Examples: Create Booking HappyPath Scenario
      | url     | responseMessage |
      | booking | 200             |
