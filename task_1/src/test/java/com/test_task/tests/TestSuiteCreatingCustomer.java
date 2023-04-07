package com.test_task.tests;

import com.test_task.configuration.BaseTest;
import com.test_task.constants.FieldsConstants;
import com.test_task.constants.UrlConstants;
import com.test_task.models.Customer;
import com.test_task.models.Person;
import com.test_task.pages.CreateCustomerPage;
import com.test_task.pages.LoginPage;
import com.test_task.utils.Utils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import java.util.Optional;
import org.apache.hc.core5.util.Asserts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Epic("Test Suite: creating a customer")
public class TestSuiteCreatingCustomer extends BaseTest {

  private LoginPage loginPage;
  private CreateCustomerPage createCustomerPage;

  @BeforeEach
  public void initPageObjects() {
    loginPage = new LoginPage(getDriver());
    createCustomerPage = new CreateCustomerPage(getDriver());
    getDriver().get(UrlConstants.BASE);
    loginPage.login();
  }

  @Test
  @Tag("#TC001")
  @DisplayName("Test Case #TC001: Creating a client with correct data.")
  @Description("Test Case #TC001: Creating a client with correct data.")
  @Story("Manager creates a new customer.")
  public void successfulCreatingCustomer() {
    getDriver().get(UrlConstants.CREATE_CUSTOMER);
    final Person newPerson = Utils.createRandomPerson();
    final Optional<Customer> newCustomer = createCustomerPage.addCustomer(
      newPerson
    );
    Asserts.check(newCustomer != null, "New customer can't be null.");
  }

  @Test
  @Tag("#TC002")
  @DisplayName(
    "Test Case #TC002: Creating a customer with a missing required field."
  )
  @Description(
    "Test Case #TC002: Creating a customer with a missing required field."
  )
  @Story(
    "Manager tries to create a new customer with a missing required field."
  )
  public void creatingCustomerWithMissingRequiredField() {
    getDriver().get(UrlConstants.CREATE_CUSTOMER);

    final Person newPerson = new Person(
      Utils.getRandomFirstName(),
      Utils.getRandomLastName(),
      ""
    );

    final Optional<Customer> newCustomer = createCustomerPage.addCustomer(
      newPerson
    );

    Asserts.check(newCustomer == null, "New customer mustn't be created.");
  }

  @Test
  @Tag("#TC003")
  @DisplayName(
    "Test Case #TC003: Creating a client with symbols (numbers, signs) in First Name and Last Name."
  )
  @Description(
    "Test Case #TC003: Creating a client with symbols (numbers, signs) in First Name and Last Name."
  )
  @Story(
    "Manager tries to create a new customer with symbols (numbers, signs) in First Name and Last Name."
  )
  public void creatingCustomerWithSymbolsAndNumbersInNameFields() {
    getDriver().get(UrlConstants.CREATE_CUSTOMER);
    final Person newPerson = new Person(
      Utils.getRandomFirstName() + Utils.getRandomNumber(5),
      Utils.getRandomLastName() + Utils.getRandomNumber(5),
      Utils.getPostCode()
    );

    final Optional<Customer> newCustomer = createCustomerPage.addCustomer(
      newPerson
    );

    Asserts.check(newCustomer != null, "New customer must be created.");
  }

  @Test
  @Tag("#TC004")
  @DisplayName(
    "Test Case #TC004: Creating a customer with incorrect data: First Name, Last Name and Post Code."
  )
  @Description(
    "Test Case #TC004: Creating a customer with incorrect data: First Name, Last Name and Post Code."
  )
  @Story(
    "Manager tries to create a customer with incorrect data: First Name, Last Name and Post Code."
  )
  public void creatingCustomerWithIncorrectData() {
    getDriver().get(UrlConstants.CREATE_CUSTOMER);

    final Person newPerson = new Person(
      Utils.getRandomString(),
      Utils.getRandomString(),
      Utils.getRandomString()
    );

    final Optional<Customer> newCustomer = createCustomerPage.addCustomer(
      newPerson
    );

    Asserts.check(newCustomer != null, "New customer must be created.");
  }

  @Test
  @Tag("#TC005")
  @DisplayName(
    "Test Case #TC005: Creating a customer with a minimum number of characters in the fields (1 character): First Name, Last Name and Post Code."
  )
  @Description(
    "Test Case #TC005: Creating a customer with a minimum number of characters in the fields (1 character): First Name, Last Name and Post Code."
  )
  @Story(
    "Manager tries to create a customer with Creating a customer with a minimum number of characters in the fields (1 character): First Name, Last Name and Post Code."
  )
  public void creatingCustomerWithUnderData() {
    getDriver().get(UrlConstants.CREATE_CUSTOMER);

    final Person newPerson = new Person(
      Utils.getRandomString(FieldsConstants.MIN_NAME_LENGTH),
      Utils.getRandomString(FieldsConstants.MIN_NAME_LENGTH),
      Utils.getRandomString(FieldsConstants.MIN_POST_CODE_LENGTH)
    );

    final Optional<Customer> newCustomer = createCustomerPage.addCustomer(
      newPerson
    );

    Asserts.check(newCustomer != null, "New customer must be created.");
  }

  @Test
  @Tag("#TC006")
  @DisplayName(
    "Test Case #TC006: Creating a customer with the maximum number of characters in the fields: First Name, Last Name and Post Code."
  )
  @Description(
    "Test Case #TC006: Creating a customer with the maximum number of characters in the fields: First Name, Last Name and Post Code."
  )
  @Story(
    "Manager tries to create a customer with Creating a customer with the maximum number of characters in the fields: First Name, Last Name and Post Code."
  )
  public void creatingCustomerWithMaximumData() {
    getDriver().get(UrlConstants.CREATE_CUSTOMER);

    final Person newPerson = new Person(
      Utils.getRandomString(FieldsConstants.MAX_NAME_LENGTH, false, false),
      Utils.getRandomString(FieldsConstants.MAX_NAME_LENGTH, false, false),
      Utils.getRandomString(FieldsConstants.MAX_NAME_LENGTH, false, false)
    );

    final Optional<Customer> newCustomer = createCustomerPage.addCustomer(
      newPerson
    );

    Asserts.check(newCustomer != null, "New customer mustn't be created.");
  }

  @Test
  @Tag("#TC007")
  @DisplayName(
    "Test Case #TC008: Attempting to add a user with existing data (Duplicate customer)."
  )
  @Description(
    "Test Case #TC008: Attempting to add a user with existing data (Duplicate customer)."
  )
  @Story("Manager tries to create a customer with existing data.")
  public void creatingExistingCustomer() {
    getDriver().get(UrlConstants.CREATE_CUSTOMER);
    final Person newPerson = Utils.createRandomPerson();

    createCustomerPage.addCustomer(newPerson);
    final Optional<Customer> theSameCustomer = createCustomerPage.addCustomer(
      newPerson
    );

    Asserts.check(theSameCustomer == null, "New customer can't be null.");
  }
}
