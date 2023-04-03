package com.test_task.pages;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.test_task.locators.CreateCustomerPageLocators;
import com.test_task.models.Customer;
import com.test_task.models.Person;

import io.qameta.allure.Step;

public class CreateCustomerPage extends Page {

  @FindBy(xpath = CreateCustomerPageLocators.firstNameField)
  public WebElement firstNameField;

  @FindBy(xpath = CreateCustomerPageLocators.lastNameField)
  public WebElement lastNameField;

  @FindBy(xpath = CreateCustomerPageLocators.postCodeField)
  public WebElement postCodeField;

  @FindBy(xpath = CreateCustomerPageLocators.addCustomerButton)
  public WebElement addCustomerButton;

  public CreateCustomerPage(WebDriver webDriver) {
    super(webDriver);

  }

  @Step("Add new customer.")
  public Optional<Customer> addCustomer(Person person) {
    try {
      if (person.firstName != null) {
        firstNameField.sendKeys(person.firstName.get());
      }
      if (person.lastName != null) {
        lastNameField.sendKeys(person.lastName.get());
      }
      if (person.postCode != null) {
        postCodeField.sendKeys(person.postCode.get());
      }

      addCustomerButton.click();

      waitForAlertDialog();

      final Alert alert = driver.switchTo().alert();

      final Optional<Integer> customerId = _getCreatedCustomerId(alert.getText());

      // ? info: Press the OK button
      alert.accept();

      if (customerId == null) {
        return null;
      }

      return Optional.of(new Customer(person, customerId.get()));

    }

    catch (Exception e) {
      return null;
    }

  }

  private Optional<Integer> _getCreatedCustomerId(String text) {

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
