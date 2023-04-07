package com.test_task.utils;

import com.github.javafaker.Faker;
import com.test_task.models.Person;

public class Utils {

  private static Faker faker = new Faker();

  public static Person createRandomPerson() {
    final Person newPerson = new Person(
      faker.name().firstName(),
      faker.name().lastName(),
      faker.address().zipCode()
    );
    return newPerson;
  }

  public static String getRandomFirstName() {
    return faker.name().firstName();
  }

  public static String getRandomLastName() {
    return faker.name().lastName();
  }

  public static String getPostCode() {
    return faker.address().zipCode();
  }

  public static String getRandomNumber(int count) {
    return faker.number().digits(count);
  }

  public static String getRandomString() {
    return faker.internet().password(10, 20, true, true);
  }

  public static String getRandomString(
    int minLength,
    int maxLength,
    boolean includeUppercase,
    boolean includeSpecial
  ) {
    return faker.lorem().characters(minLength, maxLength, false, false);
  }

  public static String getRandomString(
    int length,
    boolean includeUppercase,
    boolean includeSpecial
  ) {
    return faker.lorem().characters(length, false, false);
  }

  public static String getRandomString(int minLength, int maxLength) {
    return faker.lorem().characters(minLength, maxLength);
  }

  public static String getRandomString(int length) {
    return faker.lorem().characters(length, false, false);
  }

  public static String getRandomSentence(int wordCount) {
    return faker.lorem().sentence(wordCount);
  }

  public static String getRandomSymbol(int wordCount) {
    return faker.lorem().sentence(wordCount);
  }
}
