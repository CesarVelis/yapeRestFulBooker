Feature: Validate the behavior of GetBooking api service

  Scenario Outline: GetBooking Services

    Given Testing GetBooking method I add this guess on the GetBookingIds method to be sure the record exist "<url>"
     """
     {
        "firstname": "James",
        "lastname": "Wick",
        "totalprice": 111,
        "depositpaid": true,
        "bookingdates": {
            "checkin": "2018-01-01",
            "checkout": "2019-01-01"
        },
        "additionalneeds": "Limousine"
    }
    """

    Given GetBooking method call to "<url>" with id

    Then GetBooking Response Code "<responseMessage>" and the following fields need to be validated: "<firstname>", "<lastname>", "<totalprice>", "<depositpaid>", "<checkin>", "<checkout>", "<additionalneeds>"

    Examples:  Happy Path Scenario
     | url     | responseMessage | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
     | booking | 200             | James     | Wick     | 111        | true        | 2018-01-01 | 2019-01-01 | Limousine       |



