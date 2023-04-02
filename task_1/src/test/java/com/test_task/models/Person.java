package com.test_task.models;

import com.google.common.base.Optional;

public class Person {

    public final Optional<String> firstName;
    public final Optional<String> lastName;
    public final Optional<String> postCode;

    public Person(String firstName, String lastName, String postCode) {
        this.firstName = Optional.fromNullable(firstName);
        this.lastName = Optional.fromNullable(lastName);
        this.postCode = Optional.fromNullable(postCode);
    }

    public Person(Optional<String> firstName, Optional<String> lastName, Optional<String> postCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postCode = postCode;
    }

}
