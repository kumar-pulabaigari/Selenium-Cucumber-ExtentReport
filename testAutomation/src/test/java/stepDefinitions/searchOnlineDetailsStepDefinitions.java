package stepDefinitions;

import automation.drivers.CommonActions;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.poi.ss.formula.functions.T;

/**
 * Created by vinaykumarp on 3/24/2017.
 */
public class searchOnlineDetailsStepDefinitions extends CommonActions {

    @Given("^Navigate to the online search item$")
    public void launchApplication() throws Throwable {
        new pages.webMVP.ContentListPage().launchApplication();
    }

    @Then("^Validate Utilities item texts will be displayed$")
    public void utilitiesItemText() throws  Throwable {
        new pages.webMVP.searchOnlinePage().validateNavBarHyperlinkText();
    }

    @When("^Select the options in drop downs on the online search page$")
    public void searchByOptions() throws  Throwable{
        new pages.webMVP.searchOnlinePage().searchOption();
    }

    @Then("^Search results will be based upon search criteria$")
    public void searchResultsValidation() throws Throwable{
        new pages.webMVP.searchOnlinePage().searchResults();
    }

    @When("^Select the focus fond check box$")
    public void selectTheFocusFondCheckBox() throws Throwable{
        new pages.webMVP.searchOnlinePage().focusFondCheckBoxSelection();
    }

    @Then("^Focus funds only will be displayed$")
    public void focusFundsOnlyDisplays() throws Throwable{
        new pages.webMVP.searchOnlinePage().focusFundsOnlyDisplays();
    }

    @When("^select the ListBody view option$")
    public void selectTheListBodyViewOption() throws Throwable{
        new pages.webMVP.searchOnlinePage().selectTheListBodyViewOption();
    }

    @When("^select the sort Alphabetical in ascending order$")
    public void selectSortAlphaAscendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().selectSortAlphaAscendingOrder();
    }

    @Then("^Fund name will be sorted in the ascending order in the list body view$")
    public void ListBodySortByFundNameInAscendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().ListBodySortByFundNameInAscendingOrder();
    }

    @When("^select the sort Alphabetical in descending order$")
    public void selectSortAlphaDescendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().selectSortAlphaDescendingOrder();
    }

    @Then("^Fund name will be sorted in the descending order in the list body view$")
    public void ListBodySortByFundNameInDescendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().ListBodySortByFundNameInDescendingOrder();
    }

    @When("^select the sort percentage in ascending order$")
    public void selectSortPercentageAscendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().selectSortPercentageAscendingOrder();
    }

    @Then("^Percentage will be sorted in the ascending order in the list body view$")
    public void ListBodySortByPercentageInAscendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().ListBodySortByPercentageInAscendingOrder();
    }

    @When("^select the sort percentage in descending order$")
    public void selectSortPercentageDescendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().selectSortPercentageDescendingOrder();
    }

    @Then("^Percentage will be sorted in the descending order in the list body view$")
    public void ListBodySortByPercentageInDescendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().ListBodySortByPercentageInDescendingOrder();
    }

    @When("^select the Table Body view option$")
    public void selectTheTableBodyViewOption() throws Throwable{
        new pages.webMVP.searchOnlinePage().selectTheTableBodyViewOption();
    }

    @When("^select the Fund Name in the Alphabetical order in ascending way$")
    public void selectSortFundNameInAscendingOrderInTableBodyList() throws Throwable {
        new pages.webMVP.searchOnlinePage().selectSortFundNameInAscendingOrderInTableBodyList();
    }

    @Then("^Fund name will be sorted in the ascending order in the table body view$")
    public void TableBodySortByFundNameInAscendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().TableBodySortByFundNameInAscendingOrder();
    }

    @When("^select the Fund Name in the Alphabetical order in descending way$")
    public void selectSortFundNameInDescendingOrderInTableBodyList() throws Throwable {
        new pages.webMVP.searchOnlinePage().selectSortFundNameInDescendingOrderInTableBodyList();
    }

    @Then("^Fund name will be sorted in the descending order in the table body view$")
    public void TableBodySortByFundNameInDescendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().TableBodySortByFundNameInDescendingOrder();
    }

    @When("^select the currency in the Alphabetical order in ascending way$")
    public void selectSortCurrencyInAscendingOrderInTableBodyList() throws Throwable {
        new pages.webMVP.searchOnlinePage().selectSortCurrencyInAscendingOrderInTableBodyList();
    }

    @Then("^currency will be sorted in the ascending order in the table body view$")
    public void TableBodySortByCurrencyInAscendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().TableBodySortByCurrencyInAscendingOrder();
    }

    @When("^select the currency in the Alphabetical order in descending way$")
    public void selectSortCurrencyInDescendingOrderInTableBodyList() throws Throwable {
        new pages.webMVP.searchOnlinePage().selectSortCurrencyInDescendingOrderInTableBodyList();
    }

    @Then("^currency will be sorted in the descending order in the table body view$")
    public void TableBodySortByCurrencyInDescendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().TableBodySortByCurrencyInDescendingOrder();
    }

    @When("^select the sort 3 months percentage in ascending order$")
    public void selectSort3PercentageAscendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().selectSort3PercentageAscendingOrder();
    }

    @Then("^3 months percentages will be sorted in the ascending order in the table body view$")
    public void tableBodySortBy3PercentageInAscendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().tableBodySortBy3PercentageInAscendingOrder();
    }

    @When("^select the sort 3 months percentage in descending order$")
    public void selectSort3PercentageDescendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().selectSort3PercentageDescendingOrder();
    }

    @Then("^3 months percentages will be sorted in the descending order in the table body view$")
    public void tableBodySortBy3PercentageInDescendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().tableBodySortBy3PercentageInDescendingOrder();
    }

    @When("^select the sort 6 months percentage in ascending order$")
    public void selectSort6PercentageAscendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().selectSort6PercentageAscendingOrder();
    }

    @Then("^6 months percentages will be sorted in the ascending order in the table body view$")
    public void tableBodySortBy6PercentageInAscendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().tableBodySortBy6PercentageInAscendingOrder();
    }

    @When("^select the sort 6 months percentage in descending order$")
    public void selectSort6PercentageDescendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().selectSort6PercentageDescendingOrder();
    }

    @Then("^6 months percentages will be sorted in the descending order in the table body view$")
    public void tableBodySortBy6PercentageInDescendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().tableBodySortBy6PercentageInDescendingOrder();
    }

    @When("^select the sort 1 year percentage in ascending order$")
    public void selectSort1YearPercentageAscendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().selectSort1YearPercentageAscendingOrder();
    }

    @Then("^1 year percentages will be sorted in the ascending order in the table body view$")
    public void tableBodySortBy1YearPercentageInAscendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().tableBodySortBy1YearPercentageInAscendingOrder();
    }

    @When("^select the sort 1 year percentage in descending order$")
    public void selectSort1YearPercentageDescendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().selectSort1YearPercentageDescendingOrder();
    }

    @Then("^1 year percentages will be sorted in the descending order in the table body view$")
    public void tableBodySortBy1YearPercentageInDescendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().tableBodySortBy1YearPercentageInDescendingOrder();
    }

    @When("^select the sort 3 year percentage in ascending order$")
    public void selectSort3YearPercentageAscendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().selectSort3YearPercentageAscendingOrder();
    }

    @Then("^3 year percentages will be sorted in the ascending order in the table body view$")
    public void tableBodySortBy3YearPercentageInAscendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().tableBodySortBy3YearPercentageInAscendingOrder();
    }

    @When("^select the sort 3 year percentage in descending order$")
    public void selectSort3YearPercentageDescendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().selectSort3YearPercentageDescendingOrder();
    }

    @Then("^3 year percentages will be sorted in the descending order in the table body view$")
    public void tableBodySortBy3YearPercentageInDescendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().tableBodySortBy3YearPercentageInDescendingOrder();
    }

    @When("^select the sort 3 years SD percentage in ascending order$")
    public void selectSort3YearSDPercentageAscendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().selectSort3YearsSDPercentageAscendingOrder();
    }

    @Then("^3 years SD percentages will be sorted in the ascending order in the table body view$")
    public void tableBodySortBy3YearSDPercentageInAscendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().tableBodySortBy3YearsSDPercentageInAscendingOrder();
    }

    @When("^select the sort 3 years SD percentage in descending order$")
    public void selectSort3YearSDPercentageDescendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().selectSort3YearsSDPercentageDescendingOrder();
    }

    @Then("^3 years SD percentages will be sorted in the descending order in the table body view$")
    public void tableBodySortBy3YearSDPercentageInDescendingOrder() throws Throwable {
        new pages.webMVP.searchOnlinePage().tableBodySortBy3YearsSDPercentageInDescendingOrder();
    }

    @Then("^Pagination functionality will work fine in Table View$")
    public void tablePaginationFuncationality() throws Throwable {
        new pages.webMVP.searchOnlinePage().tablePaginationFuncationality();
    }

    @Then("^Pagination functionality will work fine in tile View$")
    public void tilePaginationFuncationality() throws Throwable {
        new pages.webMVP.searchOnlinePage().tilePaginationFuncationality();
    }

    @Then("^search results functionality will work fine in Table View$")
    public void searchResultsTable() throws Throwable {
        new pages.webMVP.searchOnlinePage().searchResultsTable();
    }

    @Then("^search results functionality will work fine in tile View$")
    public void searchResultsTile() throws Throwable {
        new pages.webMVP.searchOnlinePage().searchResultsTile();
    }

    @Then("^Validate the UI Content$")
    public void UIContent() throws Throwable {
        new pages.webMVP.searchOnlinePage().UIContent();
    }

    @When("^click on the fund name in the Table List$")
    public void FundInfoOpenTableList() throws Throwable {
        new pages.webMVP.searchOnlinePage().FundInfoOpenTableList();
    }

    @Then("^Fund Info will be displayed$")
    public void FundInfoValidate() throws Throwable {

    }

    @When("^click on the fund name in the List Body$")
    public void FundInfoOpenListBody() throws Throwable {
        new pages.webMVP.searchOnlinePage().FundInfoOpenListBody();
    }
}
