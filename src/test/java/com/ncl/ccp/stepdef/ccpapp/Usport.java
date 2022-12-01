package com.ncl.ccp.stepdef.ccpapp;

import com.ncl.ccp.pages.ccp.UsportPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class Usport {

    @Autowired
    UsportPage usportPage;

    @When("click to {string} offload option")
    public void clickToOffloadOption (String data) { usportPage.performActions(data); }
    @And("click on {string} response option")
    public void clickOnResponseOption(String data) {
        usportPage.performActions(data);
    }
    @When("navigate to {string} option from vessels offload option")
    public void navigateToOptionFromVesselsOffloadOption(String data) {
        usportPage.performActions(data);
    }
    @Then("offload event department response page should be displayed {string}")
    public void offloadEventDepartmentResponsePageShouldBeDisplayed(String data) {
        usportPage.performActions(data);
    }
    @And("update number of {string} forms field dropdown")
    public void updateNumberOfFormsFieldDropdown(String data) {
           usportPage.performActions(data);
    }
    @Then("updated offload event department response page should be displayed {string}")
    public void updatedOffloadEventDepartmentResponsePageShouldBeDisplayed(String data) {
        usportPage.performActions(data); }
    @And("click on the {string} option for the offload event created previously")
    public void clickOnTheOptionForTheOffloadEventCreatedPreviously(String data) {
        usportPage.performActions(data);
    }
    @When("click on {string} offload option")
    public void clickOnOffloadOption(String data) {
        usportPage.performActions(data);
    }
    @When("enter {string} and click on search button")
    public void enterAndClickOnSearchButton(String data) {
        usportPage.performActions(data);
    }
    @And("click on {string} button")
    public void clickOnButton(String data) {
        usportPage.performActions(data);
    }
}
