package com.test_task.models;

import java.util.Comparator;

import com.test_task.enums.TableSortOrder;

public class TableCustomersNameComparator implements Comparator<TableCustomer> {

    final TableSortOrder order;

    public TableCustomersNameComparator(TableSortOrder order) {
        this.order = order;
    }

    @Override
    public int compare(TableCustomer a, TableCustomer b) {

        if (order == TableSortOrder.alphabetically) {
            return a.firstName.get().compareToIgnoreCase(b.firstName.get());

        } else {
            return b.firstName.get().compareToIgnoreCase(a.firstName.get());

        }

    }
}
