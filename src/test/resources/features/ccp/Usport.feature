Feature: Verify updating department response

    @TC-002
Scenario: Update department response
    Given navigate to nclccp application
    When enter Username, password and click on "Login" button
    Then homepage should be displayed "UserProfile"
    When click on "vessels" offload option
    And click on "offload" response option
    Then offload event department response page should be displayed "oedrpage"
#    When enter "datavalue" and click on search button
    #And click on the "edit" option for the offload event created previously
