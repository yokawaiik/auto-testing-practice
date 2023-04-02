package com.test_task.pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import com.beust.jcommander.DynamicParameter;
import com.test_task.locators.CustomersListPageLocators;
import com.test_task.models.TableCustomer;

import io.qameta.allure.Step;

public class CustomersListPage extends Page {

  @FindBy(xpath = CustomersListPageLocators.searchCustomer)
  public WebElement searchCustomer;

  @FindAll({ @FindBy(xpath = CustomersListPageLocators.deleteButton) })
  public List<WebElement> deleteButtonList;

  @DynamicParameter
  @FindAll({ @FindBy(xpath = CustomersListPageLocators.tableRow) })
  public List<WebElement> tableRowList;

  public CustomersListPage(WebDriver webDriver) {
    super(webDriver);

  }

  @Step("Type search query in the search field.")
  public void setSearchField(String queryString) {
    searchCustomer.sendKeys(queryString);
  }

  @Step("Delete all customers.")
  public void deleteAllCustomers() {
    Iterator<WebElement> buttonIterator = deleteButtonList.iterator();
    while (buttonIterator.hasNext()) {
      buttonIterator.next().click();
      buttonIterator.remove();
    }

  }

  @Step("Sort customers by name.")
  public void sortCustomersByName() {
    final WebElement firstNameSortTypeButton = driver
        .findElement(By.xpath(CustomersListPageLocators.firstNameSortType));
    firstNameSortTypeButton.click();
  }

  @Step("Get all customers un the table.")
  public Optional<List<TableCustomer>> getAllCustomersInTheTable() {
    try {
      Iterator<WebElement> tableRowListIterator = tableRowList.iterator();

      final List<TableCustomer> tableCustomerList = new ArrayList<>();

      while (tableRowListIterator.hasNext()) {

        final WebElement row = tableRowListIterator.next();

        final List<WebElement> tableDataElements = row
            .findElements(By.xpath(CustomersListPageLocators.relativeTableData));

        final String firstName = tableDataElements.get(0).getText();
        final String lastName = tableDataElements.get(1).getText();
        final String postCode = tableDataElements.get(2).getText();

        final String[] accountNumberListParsed = tableDataElements.get(3).getText().split("//([0-9]+)");

        final ArrayList<String> accountNumberList = new ArrayList<>();

        for (int index = 0; index < accountNumberListParsed.length; index++) {
          accountNumberList.add(accountNumberListParsed[index]);
        }

        final TableCustomer tableCustomer = new TableCustomer(
            Optional.of(firstName),
            Optional.of(lastName),
            Optional.of(postCode),
            Optional.of(accountNumberList));

        tableCustomerList.add(tableCustomer);

      }

      if (tableCustomerList.isEmpty()) {
        return Optional.of(null);
      } else {
        return Optional.of(tableCustomerList);
      }

    } catch (Exception e) {
      return Optional.of(null);
    }

  }

}
