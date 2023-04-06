package com.test_task.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.test_task.locators.LoginPageLocators;
import io.qameta.allure.Step;
public class LoginPage extends Page {

  @FindBy(xpath = LoginPageLocators.loginButton)
  public WebElement loginButton;

  public LoginPage(WebDriver webDriver) {
    super(webDriver);
  }

  @Step("Log in the site like a manager.")
  public void login() {
    waitWhileElementToBeClickable(By.xpath(LoginPageLocators.loginButton));
    loginButton.click();
  }

}
