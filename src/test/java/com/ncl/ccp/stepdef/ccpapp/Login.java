package com.ncl.ccp.stepdef.ccpapp;

import com.codoid.products.exception.FilloException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import com.ncl.ccp.pages.ccp.LoginPage;

import java.io.IOException;

public class Login {

    @Autowired
    LoginPage loginPage;


    @Then("Login as Res Agent with User name, password and click on {string} button")
    public void login_as_res_agent_with_user_name_password_and_click_on_button(String data) throws FilloException, IOException {
        loginPage.performActions(data);
    }
}
