package com.test_task.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TableCustomer {

    public final Optional<String> firstName;
    public final Optional<String> lastName;
    public final Optional<String> postCode;
    public final Optional<List<String>> accountNumberList;

    public TableCustomer(Optional<String> firstName, Optional<String> lastName, Optional<String> postCode,
            Optional<List<String>> accountNumberList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postCode = postCode;

        if (accountNumberList == null) {
            this.accountNumberList = Optional.of(new ArrayList<String>());
        } else {
            this.accountNumberList = accountNumberList;
        }

    }

    @Override
    public int hashCode() {

        return (firstName.get() + lastName.get() + postCode.get() + accountNumberList.get().toString()).hashCode();
    }

}
