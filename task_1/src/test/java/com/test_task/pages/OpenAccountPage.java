package com.test_task.pages;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.test_task.locators.OpenAccountPageLocators;
import com.test_task.models.Customer;

import io.qameta.allure.Step;

public class OpenAccountPage extends Page {

  @FindBy(xpath = OpenAccountPageLocators.selectCustomerId)
  public WebElement selectCustomerId;

  @FindBy(xpath = OpenAccountPageLocators.selectCurrency)
  public WebElement selectCurrency;

  @FindBy(xpath = OpenAccountPageLocators.buttonProcess)
  public WebElement buttonProcess;

  public OpenAccountPage(WebDriver webDriver) {
    super(webDriver);

  }

  @Step("Add customer and get it account number.")
  public Optional<String> openCustomerAccount(Customer customer) {
    try {

      final Select selectCustomerIdElement = new Select(selectCustomerId);
      final Select selectCurrencyElement = new Select(selectCurrency);

      selectCustomerIdElement.selectByValue(String.valueOf(customer.id));
      selectCurrencyElement.selectByIndex(1);

      buttonProcess.click();

      waitForAlertDialog();

      final Alert alert = driver.switchTo().alert();

      final Optional<Integer> createdCustomerAccountNumber = _getCreatedCustomerAccountNumber(alert.getText());
      if (createdCustomerAccountNumber == null)
        return null;
      // Press the OK button
      alert.accept();

      return Optional.of(String.valueOf(createdCustomerAccountNumber.get()));
    } catch (Exception e) {
      return null;
    }

  }

  private Optional<Integer> _getCreatedCustomerAccountNumber(String text) {

    final String regex = "\\d+";
    final Pattern pattern = Pattern.compile(regex);
    final Matcher matcher = pattern.matcher(text);

    if (matcher.find()) {
      int customerId = Integer.parseInt(matcher.group());
      return Optional.of(customerId);
    }

    return null;

  }

}
