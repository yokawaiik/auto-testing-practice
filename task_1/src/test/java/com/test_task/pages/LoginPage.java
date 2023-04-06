package com.test_task.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import io.qameta.allure.Step;
public class LoginPage extends Page {

  @FindBy(xpath = "//button[text()='Bank Manager Login']")
  public WebElement loginButton;

  public LoginPage(WebDriver webDriver) {
    super(webDriver);
  }

  @Step("Log in the site like a manager.")
  public void login() {
    waitWhileElementToBeClickable(By.xpath("//button[text()='Bank Manager Login']"));
    loginButton.click();
  }

}
