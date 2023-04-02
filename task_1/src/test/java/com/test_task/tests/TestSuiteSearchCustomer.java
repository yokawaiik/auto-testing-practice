package com.test_task.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.hc.core5.util.Asserts;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.support.PageFactory;

import com.test_task.configuration.JUnitTestBase;
import com.test_task.constants.UrlConstants;
import com.test_task.locators.LoginPageLocators;
import com.test_task.models.Customer;
import com.test_task.models.Person;
import com.test_task.models.TableCustomer;
import com.test_task.pages.CreateCustomerPage;
import com.test_task.pages.CustomersListPage;
import com.test_task.pages.LoginPage;
import com.test_task.pages.OpenAccountPage;
import com.test_task.utils.Utils;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;

@Epic("Test Suite: search a customer")
public class TestSuiteSearchCustomer extends JUnitTestBase {

  private LoginPage loginPage;
  private CustomersListPage customersListPage;
  private CreateCustomerPage createCustomerPage;
  private OpenAccountPage openAccountPage;

  List<Customer> customers;

  @BeforeEach
  public void initPageObjects() {
    loginPage = PageFactory.initElements(driver, LoginPage.class);
    customersListPage = PageFactory.initElements(driver, CustomersListPage.class);
    createCustomerPage = PageFactory.initElements(driver, CreateCustomerPage.class);
    openAccountPage = PageFactory.initElements(driver, OpenAccountPage.class);

    customers = new ArrayList<>();

    // ? info: login manager
    driver.get(UrlConstants.base);

    loginPage.login();

  }

  @BeforeEach
  public void initBaseTestState() {
    // ? info: remove all customers
    driver.get(UrlConstants.customersList);
    customersListPage.deleteAllCustomers();

    // ? info: add clients

    driver.get(UrlConstants.createCustomer);

    for (int index = 0; index < 5; index++) {
      final Person newPerson = Utils.createRandomPerson();
      final Optional<Customer> newCustomer = createCustomerPage.addCustomer(newPerson);
      Asserts.check(newCustomer.isPresent(), "New customer can't be null.");
      customers.add(newCustomer.get());
    }

    // ? info: add accounts

    driver.get(UrlConstants.addAccountToClient);

    for (int index = 0; index < customers.size(); index++) {
      final Customer currentCustomer = customers.get(index);
      Optional<String> oppenedAccountNumber = openAccountPage.openCustomerAccount(currentCustomer);
      Asserts.check(oppenedAccountNumber.isPresent(), "Account must be created.");
      currentCustomer.addAccountNumber(oppenedAccountNumber.get());

    }

  }

  @AfterEach
  public void disposeTestSuiteState() {
    // clear cookies
    driver.manage().deleteAllCookies();

  }

  @Test
  @Tag("#TC012")
  @DisplayName("Test Case #TC012: Searching for a client by field: First Name.")
  @Description("Test Case #TC012: Searching for a client by field: First Name.")
  @Story("Manager search for a customer by by field: First Name.")
  public void searchForCustomersByFirstName() {
    driver.get(UrlConstants.customersList);

    // todo: remove all clients clients

    // todo: add clients

    // todo: write query

    // todo: check

    System.out.println("test finished");

    final Optional<List<TableCustomer>> allCustomersInTheTable = customersListPage.getAllCustomersInTheTable();

    // System.out.println(allCustomersInTheTable)
    // try {
    // Thread.sleep(10000);
    // } catch (InterruptedException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
  }



  @Test
  @Tag("#TC013")
  @DisplayName("Test Case #TC013: Searching for a client by field: Last Name.")
  @Description("Test Case #TC013: Searching for a client by field: Last Name.")
  @Story("Manager search for a customer by field: Last Name.")
  public void searchForCustomersByFieldLastName() {
    driver.get(UrlConstants.customersList);

  }


  @Test
  @Tag("#TC014")
  @DisplayName("Test Case #TC014: Searching for a client by field: Post Code.")
  @Description("Test Case #TC014: Searching for a client by field: Post Code.")
  @Story("Manager search for a customer by field: Post Code.")
  public void searchForCustomersByFieldPostCode() {
    driver.get(UrlConstants.customersList);

  }

  @Test
  @Tag("#TC015")
  @DisplayName("Test Case #TC015: Searching for a client by field: Account Number.")
  @Description("Test Case #TC015: Searching for a client by field: Account Number.")
  @Story("Manager search for a customer by field: Account Number.")
  public void searchForCustomersByFieldAccountNumber() {
    driver.get(UrlConstants.customersList);

  }

  @Test
  @Tag("#TC016")
  @DisplayName("Test Case #TC016: Searching for non-existent data.")
  @Description("Test Case #TC016: Searching for non-existent data.")
  @Story("Manager tries search for a customer by non-existent data.")
  public void searchForCustomerByNonExistentData() {
    driver.get(UrlConstants.customersList);

  }

  @Test
  @Tag("#TC017")
  @DisplayName("Test Case #TC017: Performing searches with merged data.")
  @Description("Test Case #TC017: Performing searches with merged data.")
  @Story("Manager tries search for a customer by performing searches with merged data.")
  public void searchForCustomerByMergedSearchTerms() {
    driver.get(UrlConstants.customersList);

  }

  @Test
  @Tag("#TC018")
  @DisplayName("Test Case #TC018: Trying to search with an empty search term.")
  @Description("Test Case #TC018: Trying to search with an empty search term.")
  @Story("Manager tries search for a customer by an empty search term.")
  public void searchForCustomerByAnEmptySearchTerm() {
    driver.get(UrlConstants.customersList);

  }

  @Test
  @Tag("#TC019")
  @DisplayName("Test Case #TC019: Search for clients with the same data in one of the fields.")
  @Description("Test Case #TC019: Search for clients with the same data in one of the fields.")
  @Story("Manager tries to search for customers with the same data in one of the fields.")
  public void searchForCustomersWithTheSameData() {
    driver.get(UrlConstants.customersList);

  }



}


// try {
// Thread.sleep(10000);
// } catch (InterruptedException e) {
// e.printStackTrace();
// }