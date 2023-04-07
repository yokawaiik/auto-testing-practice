package com.test_task.pages;

import com.test_task.models.TableCustomer;
import io.qameta.allure.Step;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CustomersListPage extends Page {

  @FindBy(xpath = "//input[@ng-model='searchCustomer']")
  public WebElement searchCustomer;

  @FindBy(
    xpath = "//button[contains(@ng-click,'deleteCust') and contains(string(),'Delete')]"
  )
  public List<WebElement> deleteButtonList;

  @FindBy(
    xpath = "//table[contains(@class,'table')]//tr[contains(@class,'ng-scope')]"
  )
  public List<WebElement> tableRowList;

  public CustomersListPage(WebDriver webDriver) {
    super(webDriver);
    PageFactory.initElements(driver, this);
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
    final WebElement firstNameSortTypeButton = driver.findElement(
      By.xpath(
        "//a[contains(@ng-click,'sortType') and contains(string(),'First Name')]"
      )
    );
    firstNameSortTypeButton.click();
  }

  @Step("Get all customers un the table.")
  public Optional<List<TableCustomer>> getAllCustomersInTheTable() {
    try {
      Iterator<WebElement> tableRowListIterator = tableRowList.iterator();

      final List<TableCustomer> tableCustomerList = new ArrayList<>();

      while (tableRowListIterator.hasNext()) {
        final WebElement row = tableRowListIterator.next();

        final List<WebElement> tableDataElements = row.findElements(
          By.xpath(".//td")
        );

        final String firstName = tableDataElements.get(0).getText();
        final String lastName = tableDataElements.get(1).getText();
        final String postCode = tableDataElements.get(2).getText();

        final String[] accountNumberListParsed = tableDataElements
          .get(3)
          .getText()
          .split("//([0-9]+)");

        final ArrayList<String> accountNumberList = new ArrayList<>();

        for (int index = 0; index < accountNumberListParsed.length; index++) {
          accountNumberList.add(accountNumberListParsed[index]);
        }

        final TableCustomer tableCustomer = new TableCustomer(
          Optional.of(firstName),
          Optional.of(lastName),
          Optional.of(postCode),
          Optional.of(accountNumberList)
        );

        tableCustomerList.add(tableCustomer);
      }

      if (tableCustomerList.isEmpty()) {
        return null;
      } else {
        return Optional.of(tableCustomerList);
      }
    } catch (Exception e) {
      System.out.println("Exception: " + e);
      return null;
    }
  }
}
