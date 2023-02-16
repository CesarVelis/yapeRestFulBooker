Feature: Validate the behavior of GetBookingIds api service

  Scenario: As a Precondition we need to add a new guess to be sure the data will be retrieved


    Given I add this guess on the GetBookingIds method to be sure the record exist "booking"
     """
     {
        "firstname": "Jhon",
        "lastname": "Wick",
        "totalprice": 777,
        "depositpaid": true,
        "bookingdates": {
            "checkin": "2023-02-15",
            "checkout": "2023-02-28"
        },
        "additionalneeds": "Live Dog"
    }
    """

  Scenario Outline: GetBookingIds Services

    When GetBookingIds method call to "<url>" with "<firstname>" and "<lastname>" and "<checkin>" and "<checkout>"

    Then GetBookingIds Response Code "<responseMessage>" and "<responseElement>" is validated

    Examples: All ids Scenario
      | url     | responseMessage | firstname | lastname | checkin | checkout | responseElement |
      | booking | 200             |           |          |         |          | bookingid       |


    Examples: Filter by FirstName Scenario
      | url     | responseMessage | firstname | lastname | checkin | checkout | responseElement |
      | booking | 200             | Jhon      |          |         |          | bookingid       |

    Examples: Filter by LastName Scenario
      | url     | responseMessage | firstname | lastname | checkin | checkout | responseElement |
      | booking | 200             |           | Wick    |         |          | bookingid       |

    Examples: Filter by FirsName / LastName Scenario
      | url     | responseMessage | firstname | lastname | checkin | checkout | responseElement |
      | booking | 200             | Jhon      | Wick    |         |          | bookingid       |

    Examples: Filter by Checkin / CheckOut Scenario
      | url     | responseMessage | firstname | lastname | checkin    | checkout   | responseElement |
      | booking | 200             |           |          | 2023-02-15 | 2023-02-28 | bookingid       |

