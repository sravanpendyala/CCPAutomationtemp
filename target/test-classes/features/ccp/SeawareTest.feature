@AZIT-24
Feature: Azamara Seaware Touch website verification

  @AZIT-24_005
  Scenario: Verify Res Agent is able to Add From date and to date to find voyages
    Given navigate to seaware touch application
    When click on "Login" on home page
    And Login as Res Agent with User name, password and click on "Login" button
    And click on "MainMenu" button and new reservation
    And click on "Continue" button on down right corner
    Then date field "FromTo" should be displayed

  @AZIT-24_006
  Scenario: Verify Res Agent is able to select the Voyages from Voyage Listing Page
    Given navigate to seaware touch application
    When click on "Login" on home page
    And Login as Res Agent with User name, password and click on "Login" button
    And click on "MainMenu" button and new reservation
    And click on "Continue" button on down right corner
    Then date field "FromTo" should be displayed
    And select date from "Calender"
    And click on "Search" cruise
    And Select "CruiseSelect" from searchcruise table
    And click on "Continue" button on down right corner
    Then navigates to the "Staterooms" Page

  @AZIT-24_007
  Scenario: Verify Res Agent Should be able to view all available Voyages and their High level details
    Given navigate to seaware touch application
    When click on "Login" on home page
    And Login as Res Agent with User name, password and click on "Login" button
    And click on "MainMenu" button and new reservation
    And click on "Continue" button on down right corner
    Then date field "FromTo" should be displayed
    And select date from "Calender"
    And click on "Search" cruise
    Then validates "ShipDetails" under grid

  @AZIT-24_008
  Scenario: Verify Res Agent Should be able to view  voyages and  check  complete details of any one voyage
    Given navigate to seaware touch application
    When click on "Login" on home page
    And Login as Res Agent with User name, password and click on "Login" button
    And click on "MainMenu" button and new reservation
    And click on "Continue" button on down right corner
    Then date field "FromTo" should be displayed
    When select date from "Calender"
    And click on "Search" cruise
    And select ship from cruise list and verify the ship details
    And close the dialog popup details
    And select cruise name from cruise list and verify the cruise name details
    And close the dialog popup details
    And select Iicon from cruise list and verify the Iicon details
    And close the dialog popup details
    And select Location from cruise list and verify the Location details
    And close the dialog popup details

