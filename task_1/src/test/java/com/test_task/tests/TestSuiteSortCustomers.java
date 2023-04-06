package com.test_task.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.hc.core5.util.Asserts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.support.PageFactory;

import com.test_task.configuration.BaseTest;
import com.test_task.constants.UrlConstants;
import com.test_task.enums.TableSortOrder;
import com.test_task.models.Customer;
import com.test_task.models.Person;
import com.test_task.models.TableCustomer;
import com.test_task.models.TableCustomersNameComparator;
import com.test_task.pages.CreateCustomerPage;
import com.test_task.pages.CustomersListPage;
import com.test_task.pages.LoginPage;
import com.test_task.pages.OpenAccountPage;
import com.test_task.utils.Utils;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

@Epic("Test Suite: search a customer")
public class TestSuiteSortCustomers extends BaseTest {

  private LoginPage loginPage;
  private CustomersListPage customersListPage;
  private CreateCustomerPage createCustomerPage;
  private OpenAccountPage openAccountPage;

  List<Customer> customers;

  @BeforeEach
  public void initPageObjects() {
    loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
    customersListPage = PageFactory.initElements(getDriver(), CustomersListPage.class);
    createCustomerPage = PageFactory.initElements(getDriver(), CreateCustomerPage.class);
    openAccountPage = PageFactory.initElements(getDriver(), OpenAccountPage.class);

    customers = new ArrayList<>();

    getDriver().get(UrlConstants.BASE);

    loginPage.login();

  }

  @BeforeEach
  @Step("Delete standarts customers, add new customers with accounts.")
  public void initBaseTestState() {
    // ? info: remove all customers
    getDriver().get(UrlConstants.CUSTOMERS_LIST);
    customersListPage.deleteAllCustomers();

    getDriver().get(UrlConstants.CREATE_CUSTOMER);

    for (int index = 0; index < 5; index++) {
      final Person newPerson = Utils.createRandomPerson();
      final Optional<Customer> newCustomer = createCustomerPage.addCustomer(newPerson);
      Asserts.check(newCustomer.isPresent(), "New customer can't be null.");
      customers.add(newCustomer.get());
    }

    getDriver().get(UrlConstants.ADD_ACCOUNT_TO_CLIENT);

    for (int index = 0; index < customers.size(); index++) {
      final Customer currentCustomer = customers.get(index);
      Optional<String> oppenedAccountNumber = openAccountPage.openCustomerAccount(currentCustomer);
      Asserts.check(oppenedAccountNumber.isPresent(), "Account must be created.");
      currentCustomer.addAccountNumber(oppenedAccountNumber.get());

    }

  }


  @Test
  @Tag("#TC008")
  @DisplayName("Test Case #TC009: sort alphabetically.")
  @Description("Test Case #TC009: sort alphabetically.")
  @Story("User opened customers list page and sort table by name reverse alphabetical order.")
  public void sortByNameAlphabetically() {
    getDriver().get(UrlConstants.CUSTOMERS_LIST);

    final List<TableCustomer> localSortedAllCustomersInTheTable = customersListPage
        .getAllCustomersInTheTable().get();

    Collections.sort(localSortedAllCustomersInTheTable,
        new TableCustomersNameComparator(TableSortOrder.alphabetically));

    customersListPage.sortCustomersByName();
    customersListPage.sortCustomersByName();

    final List<TableCustomer> sortedAllCustomersInTheTable = customersListPage.getAllCustomersInTheTable().get();

    for (int index = 0; index < sortedAllCustomersInTheTable.size(); index++) {

      final String firstString = localSortedAllCustomersInTheTable.get(index).firstName.get();
      final String secondString = sortedAllCustomersInTheTable.get(index).firstName.get();

      Asserts.check(firstString.equalsIgnoreCase(secondString),
          "All customers in the table must be sorted alphabetically.");

    }

  }

  @Test
  @Tag("#TC009")
  @DisplayName("Test Case #TC010: sort by name reverse alphabetical order.")
  @Description("Test Case #TC010: sort by name reverse alphabetical order.")
  @Story("User opened customers list page and sort table by name reverse alphabetical order.")
  public void sortByNameReverseAlphabetically() {
    getDriver().get(UrlConstants.CUSTOMERS_LIST);

    final List<TableCustomer> localSortedAllCustomersInTheTable = customersListPage
        .getAllCustomersInTheTable().get();

    Collections.sort(localSortedAllCustomersInTheTable,
        new TableCustomersNameComparator(TableSortOrder.reverseAlphabetically));

    customersListPage.sortCustomersByName();

    final List<TableCustomer> sortedAllCustomersInTheTable = customersListPage.getAllCustomersInTheTable().get();

    for (int index = 0; index < sortedAllCustomersInTheTable.size(); index++) {

      final String firstString = localSortedAllCustomersInTheTable.get(index).firstName.get();
      final String secondString = sortedAllCustomersInTheTable.get(index).firstName.get();

      Asserts.check(firstString.equalsIgnoreCase(secondString),
          "All customers in the table must be sorted reverse alphabetically.");

    }

  }

  @Test
  @Tag("#TC010")
  @DisplayName("Test Case #TC011: Performing a sort with an empty customer table.")
  @Description("Test Case #TC011: Performing a sort with an empty customer table.")
  @Story("User opened customers list page with empty table and tries to sort by name.")
  public void sortEmptyTable() {
    getDriver().get(UrlConstants.CUSTOMERS_LIST);

    customersListPage.deleteAllCustomers();
    customersListPage.sortCustomersByName();
    final Optional<List<TableCustomer>> localSortedAllCustomersInTheTable = customersListPage
        .getAllCustomersInTheTable();

    Asserts.check(localSortedAllCustomersInTheTable == null, "Table must be empty.");
  }

}
