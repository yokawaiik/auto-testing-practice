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
import java.util.stream.Stream;
import org.apache.hc.core5.util.Asserts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

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
    getDriver().get(UrlConstants.CREATE_CUSTOMER);
  }

  static Stream<Person> argsProviderFactoryForPositiveTests() {
    return Stream.of(
      Utils.createRandomPerson(),
      new Person(
        Utils.getRandomFirstName() + Utils.getRandomNumber(5),
        Utils.getRandomLastName() + Utils.getRandomNumber(5),
        Utils.getPostCode()
      ),
      new Person(
        Utils.getRandomString(),
        Utils.getRandomString(),
        Utils.getRandomString()
      ),
      new Person(
        Utils.getRandomString(FieldsConstants.MIN_NAME_LENGTH),
        Utils.getRandomString(FieldsConstants.MIN_NAME_LENGTH),
        Utils.getRandomString(FieldsConstants.MIN_POST_CODE_LENGTH)
      ),
      new Person(
        Utils.getRandomString(FieldsConstants.MAX_NAME_LENGTH, false, false),
        Utils.getRandomString(FieldsConstants.MAX_NAME_LENGTH, false, false),
        Utils.getRandomString(FieldsConstants.MAX_NAME_LENGTH, false, false)
      )
    );
  }

  @Tag("Test Cases #TC: 001, 003, 004, 005, 006")
  @DisplayName(
    "Test Cases #TC: 001, 003, 004, 005, 006: Creating client (Positive tests)."
  )
  @Description(
    "Test Cases #TC: 001, 003, 004, 005, 006: Creating client (Positive tests)."
  )
  @Story("Manager creates a new customer.")
  @ParameterizedTest
  @MethodSource("argsProviderFactoryForPositiveTests")
  public void positiveCreatingCustomer(Person person) {
    final Optional<Customer> newCustomer = createCustomerPage.addCustomer(
      person
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

    Asserts.check(newCustomer == null, "New client should not be found.");
  }

  @Test
  @Tag("#TC007")
  @DisplayName(
    "Test Case #TC007: Attempting to add a user with existing data (Duplicate customer)."
  )
  @Description(
    "Test Case #TC007: Attempting to add a user with existing data (Duplicate customer)."
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
