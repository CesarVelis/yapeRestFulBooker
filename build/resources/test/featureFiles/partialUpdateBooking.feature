Feature: Validate the behavior of PartialUpdateBooking api service

  Scenario Outline: Validate PartialUpdateBooking Services - HappyPath

    Given using PartialUpdateBooking I want to add a new guest using the Booking service using the following data: "<url>" so we can edited later
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

    Then using PartialUpdateBooking I want to validate a successful code was received: "<responseMessage>"

    Then using PartialUpdateBooking as a operator i realize the record was added wrong so i want update "<url>" that record using the following information
    """
    {
    "firstname" : "Charlie",
    "lastname" : "Brown"
     }
    """

    Then I can validate that i receive the final guess data update
    """
{
    "firstname": "Charlie",
    "lastname": "Brown",
    "totalprice": 444,
    "depositpaid": true,
    "bookingdates": {
        "checkin": "2023-02-15",
        "checkout": "2023-02-28"
    },
    "additionalneeds": "Limousine"
}
    """

    Examples: Create Booking HappyPath Scenario
      | url     | responseMessage |
      | booking | 200             |
