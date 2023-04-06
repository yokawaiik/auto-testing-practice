package com.test_task.tests;

import java.util.ArrayList;
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
public class TestSuiteSearchCustomer extends BaseTest {

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

    getDriver().get(UrlConstants.base);

    loginPage.login();
  }

  @BeforeEach
  public void initBaseTestState() {
    // remove all customers
    getDriver().get(UrlConstants.customersList);
    customersListPage.deleteAllCustomers();

    getDriver().get(UrlConstants.createCustomer);

    for (int index = 0; index < 5; index++) {
      final Person newPerson = Utils.createRandomPerson();
      final Optional<Customer> newCustomer = createCustomerPage.addCustomer(newPerson);
      Asserts.check(newCustomer.isPresent(), "New customer can't be null.");
      customers.add(newCustomer.get());
    }

    getDriver().get(UrlConstants.addAccountToClient);

    for (int index = 0; index < customers.size(); index++) {
      final Customer currentCustomer = customers.get(index);
      Optional<String> oppenedAccountNumber = openAccountPage.openCustomerAccount(currentCustomer);
      Asserts.check(oppenedAccountNumber.isPresent(), "Account must be created.");
      currentCustomer.addAccountNumber(oppenedAccountNumber.get());
    }
  }

  @AfterEach
  public void disposeTestSuiteState() {
    getDriver().manage().deleteAllCookies();
  }

  @Test
  @Tag("#TC011")
  @DisplayName("Test Case #TC012: Searching for a client by field: First Name.")
  @Description("Test Case #TC012: Searching for a client by field: First Name.")
  @Story("Manager search for a customer by by field: First Name.")
  public void searchForCustomersByFirstName() {
    getDriver().get(UrlConstants.customersList);

    final Customer customer = customers.get(0);

    customersListPage.setSearchField(customer.firstName.get());

    final Optional<List<TableCustomer>> allCustomersInTheTable = customersListPage.getAllCustomersInTheTable();
    Asserts.check(allCustomersInTheTable.isPresent(), "Search query mustn't be empty.");

    List<TableCustomer> foundCustomers = new ArrayList<TableCustomer>();
    for (int index = 0; index < allCustomersInTheTable.get().size(); index++) {
      final TableCustomer searchResultCustomer = allCustomersInTheTable.get().get(index);
      if (searchResultCustomer.firstName.get().equalsIgnoreCase(customer.firstName.get())) {
        foundCustomers.add(searchResultCustomer);
      }
    }

    Asserts.check(foundCustomers.isEmpty() == false, "Customer must be found in the table.");
  }

  @Test
  @Tag("#TC012")
  @DisplayName("Test Case #TC013: Searching for a client by field: Last Name.")
  @Description("Test Case #TC013: Searching for a client by field: Last Name.")
  @Story("Manager search for a customer by field: Last Name.")
  public void searchForCustomersByFieldLastName() {
    getDriver().get(UrlConstants.customersList);

    final Customer customer = customers.get(0);

    customersListPage.setSearchField(customer.lastName.get());

    final Optional<List<TableCustomer>> allCustomersInTheTable = customersListPage.getAllCustomersInTheTable();

    Asserts.check(allCustomersInTheTable.isPresent(), "Search query mustn't be empty.");

    List<TableCustomer> foundCustomers = new ArrayList<TableCustomer>();
    for (int index = 0; index < allCustomersInTheTable.get().size(); index++) {
      final TableCustomer searchResultCustomer = allCustomersInTheTable.get().get(index);
      if (searchResultCustomer.lastName.get() == customer.lastName.get()) {
        foundCustomers.add(searchResultCustomer);
      }
    }

    Asserts.check(foundCustomers.isEmpty() != false, "Customer must be found in the table.");
  }

  @Test
  @Tag("#TC013")
  @DisplayName("Test Case #TC014: Searching for a client by field: Post Code.")
  @Description("Test Case #TC014: Searching for a client by field: Post Code.")
  @Story("Manager search for a customer by field: Post Code.")
  public void searchForCustomersByFieldPostCode() {
    getDriver().get(UrlConstants.customersList);

    final Customer customer = customers.get(0);

    customersListPage.setSearchField(customer.postCode.get());

    final Optional<List<TableCustomer>> allCustomersInTheTable = customersListPage.getAllCustomersInTheTable();

    List<TableCustomer> foundCustomers = new ArrayList<TableCustomer>();
    for (int index = 0; index < allCustomersInTheTable.get().size(); index++) {
      final TableCustomer searchResultCustomer = allCustomersInTheTable.get().get(index);
      if (searchResultCustomer.postCode.get().equals(customer.postCode.get())) {
        foundCustomers.add(searchResultCustomer);
      }
    }

    Asserts.check(!foundCustomers.isEmpty(), "Customer must be found in the table.");
  }

  @Test
  @Tag("#TC014")
  @DisplayName("Test Case #TC015: Searching for a client by field: Account Number.")
  @Description("Test Case #TC015: Searching for a client by field: Account Number.")
  @Story("Manager search for a customer by field: Account Number.")
  public void searchForCustomersByFieldAccountNumber() {
    getDriver().get(UrlConstants.customersList);

    final Customer customer = customers.get(0);

    customersListPage.setSearchField(customer.accountNumberList.get().get(0));

    final Optional<List<TableCustomer>> allCustomersInTheTable = customersListPage.getAllCustomersInTheTable();
    Asserts.check(allCustomersInTheTable.isPresent(), "Search query mustn't be empty.");

    List<TableCustomer> foundCustomers = new ArrayList<TableCustomer>();
    for (int index = 0; index < allCustomersInTheTable.get().size(); index++) {
      final TableCustomer searchResultCustomer = allCustomersInTheTable.get().get(index);
      if (searchResultCustomer.accountNumberList.get().contains(customer.accountNumberList.get().get(0))) {
        foundCustomers.add(searchResultCustomer);
      }
    }

    Asserts.check(!foundCustomers.isEmpty(), "Customer must be found in the table.");
  }

  @Test
  @Tag("#TC015")
  @DisplayName("Test Case #TC016: Searching for non-existent data.")
  @Description("Test Case #TC016: Searching for non-existent data.")
  @Story("Manager tries search for a customer by non-existent data.")
  public void searchForCustomerByNonExistentData() {
    getDriver().get(UrlConstants.customersList);

    final String randomQuery = Utils.getRandomString();

    customersListPage.setSearchField(randomQuery);

    final Optional<List<TableCustomer>> allCustomersInTheTable = customersListPage.getAllCustomersInTheTable();

    Asserts.check(allCustomersInTheTable == null, "Search result must be empty.");
  }

  @Test
  @Tag("#TC016")
  @DisplayName("Test Case #TC017: Performing searches with merged data.")
  @Description("Test Case #TC017: Performing searches with merged data.")
  @Story("Manager tries search for a customer by performing searches with merged data.")
  public void searchForCustomerByMergedSearchTerms() {

    getDriver().get(UrlConstants.customersList);

    final Customer customer = customers.get(0);

    final String mergedQuery = customer.firstName.get() + " " + customer.lastName.get() + " " + customer.postCode.get()
        + " " + String.join(" ", customer.accountNumberList.get());

    customersListPage.setSearchField(mergedQuery);

    final Optional<List<TableCustomer>> allCustomersInTheTable = customersListPage.getAllCustomersInTheTable();

    Asserts.check(allCustomersInTheTable == null, "Search result mustn't be empty.");
  }

  @Test
  @Tag("#TC017")
  @DisplayName("Test Case #TC018: Trying to search with an empty search term.")
  @Description("Test Case #TC018: Trying to search with an empty search term.")
  @Story("Manager tries search for a customer by an empty search term.")
  public void searchForCustomerByAnEmptySearchTerm() {
    getDriver().get(UrlConstants.customersList);

    final Optional<List<TableCustomer>> allCustomersInTheTableBeforeQuery = customersListPage
        .getAllCustomersInTheTable();

    customersListPage.setSearchField("");

    final Optional<List<TableCustomer>> allCustomersInTheTableAfterQuery = customersListPage
        .getAllCustomersInTheTable();

    for (int index = 0; index < allCustomersInTheTableBeforeQuery.get().size(); index++) {
      final TableCustomer tableCustomerBefore = allCustomersInTheTableBeforeQuery.get().get(index);
      final TableCustomer tableCustomerAfter = allCustomersInTheTableAfterQuery.get().get(index);

      Asserts.check(tableCustomerBefore.hashCode() == tableCustomerAfter.hashCode(),
          "The data in the table should not have changed.");
    }
  }

  @Test
  @Tag("#TC018")
  @DisplayName("Test Case #TC019: Search for clients with the same data in one of the fields.")
  @Description("Test Case #TC019: Search for clients with the same data in one of the fields.")
  @Story("Manager tries to search for customers with the same data in one of the fields.")
  public void searchForCustomersWithTheSameData() {
    getDriver().get(UrlConstants.createCustomer);

    final Customer customer = customers.get(0);
    final Person newPerson = new Person(customer.firstName.get(), customer.lastName.get(), Utils.getPostCode());

    final Optional<Customer> newCustomer = createCustomerPage.addCustomer(newPerson);
    Asserts.check(newCustomer != null, "New customer can't be null.");

    customers.add(newCustomer.get());
    getDriver().get(UrlConstants.customersList);
    final String query = customer.firstName.get();
    customersListPage.setSearchField(query);

    final Optional<List<TableCustomer>> allCustomersInTheTable = customersListPage.getAllCustomersInTheTable();

    List<TableCustomer> foundCustomers = new ArrayList<TableCustomer>();
    for (int index = 0; index < allCustomersInTheTable.get().size(); index++) {
      final TableCustomer searchResultCustomer = allCustomersInTheTable.get().get(index);

      if (searchResultCustomer.firstName.get().equalsIgnoreCase(customer.firstName.get())) {
        foundCustomers.add(searchResultCustomer);
      }

    }
    Asserts.check(foundCustomers.isEmpty() == false, "Search result mustn't be empty.");
    Asserts.check(foundCustomers.size() >= 2, "Customer must be found in the table.");
  }

}
