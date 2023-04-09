package com.test_task.models;

import com.google.common.base.Optional;

public class Person {

  public final Optional<String> firstName;
  public final Optional<String> lastName;
  public final Optional<String> postCode;

  public Person(String firstName, String lastName, String postCode) {
    this.firstName = Optional.of(firstName);
    this.lastName = Optional.of(lastName);
    this.postCode = Optional.of(postCode);
  }

  public Person(
    Optional<String> firstName,
    Optional<String> lastName,
    Optional<String> postCode
  ) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.postCode = postCode;
  }
}
