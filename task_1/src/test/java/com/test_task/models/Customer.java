package com.test_task.models;

import java.util.ArrayList;

import com.google.common.base.Optional;

public class Customer {

    public int id;
    public final Optional<String> firstName;
    public final Optional<String> lastName;
    public final Optional<String> postCode;
    public final Optional<ArrayList<String>> accountNumberList;

    public Customer(Optional<String> firstName, Optional<String> lastName, Optional<String> postCode,
            Optional<ArrayList<String>> accountNumberList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postCode = postCode;

        if (accountNumberList == null) {
            this.accountNumberList = Optional.of(new ArrayList<String>());
        } else {
            this.accountNumberList = accountNumberList;
        }

    }

    public Customer(String firstName, String lastName, String postCode) {
        this.firstName = Optional.of(firstName);
        this.lastName = Optional.of(lastName);
        this.postCode = Optional.of(postCode);
        this.accountNumberList = Optional.of(new ArrayList<String>());

    }

    public Customer(Person person, int id) {
        this.id = id;
        this.firstName = Optional.of(person.firstName.get());
        this.lastName = Optional.of(person.lastName.get());
        this.postCode = Optional.of(person.postCode.get());
        this.accountNumberList = Optional.of(new ArrayList<String>());
    }

    public void addAccountNumber(String accountNumber) {
        accountNumberList.get().add((accountNumber));
    }

}
