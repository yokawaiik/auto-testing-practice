package com.test_task.locators;


public class CustomersListPageLocators {
    public static final String searchCustomer = "//input[@ng-model='searchCustomer']";
    public static final String firstNameSortType = "//a[contains(@ng-click,'sortType') and contains(string(),'First Name')]";
    /// deleteButton - it can be a lot of exemplars
    public static final String deleteButton = "//button[contains(@ng-click,'deleteCust') and contains(string(),'Delete')]";
    /// tableRow, relativeTableData, tableData - it can be a lot of exemplars
    public static final String tableRow = "//table[contains(@class,'table')]//tr[contains(@class,'ng-scope')]";
    public static final String relativeTableData = ".//td";
    public static final String tableData = "//table[contains(@class,'table')]//tr[contains(@class,'ng-scope')]//td";

}
