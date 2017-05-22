package scripts;

import automation.dataDriver.ExcelReader;
import automation.drivers.CommonActions;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.mobile.*;


public class Mobile_Steps extends CommonActions {

    public ExcelReader xlsrdr = new ExcelReader(System.getProperty("user.dir") + "\\TestData\\TestClassDetails.xls", "TestData");

    @Given("^User has Logged In to PayLah Application$")
    public void loggedInPaylahApp() throws Throwable {
        try {
            String testcasename = CucumberStep.scenarioName;
            String Username = xlsrdr.getData(testcasename, "USERNAME");
            String Password = xlsrdr.getData(testcasename, "PASSWORD");
            String SecurePwd = xlsrdr.getData(testcasename, "SecurePwd");
            new PayLahLoginViaSignUp().pre_login();
            new PayLahLoginViaSignUp().login(Username, Password, SecurePwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @And("^User selects Send Money option$")
    public void homePageDisplayed() throws Throwable {
        String testcasename = CucumberStep.scenarioName;
        String EnterPayLahPwd = xlsrdr.getData(testcasename, "PayLahPWD");
        new PayLahHome().authentication(EnterPayLahPwd);
        new PayLahHome().sendMoney();
        System.out.println("xyz");
    }

    @When("^User enter details for Sending Money and submit$")
    public void clickSendBtn() throws Throwable {
        String testcasename = CucumberStep.scenarioName;
        String MoneyToSend = xlsrdr.getData(testcasename, "MoneyToSend");
        String MobileNum = xlsrdr.getData(testcasename, "MobileNumber");
        new SendMoneyPage().sendAmountfromAccount(MoneyToSend, MobileNum);

    }

    @Then("^Send money successful message should be displayed$")
    public void sendMoneySuccess() {
        System.out.println("xyz");
    }

    @And("^User selects Request Money option$")
    public void requestMoneyOption() throws Throwable {
        String testcasename = CucumberStep.scenarioName;
        String EnterPayLahPwd = xlsrdr.getData(testcasename, "PayLahPWD");
        new PayLahHome().authentication(EnterPayLahPwd);
        new PayLahHome().requestMoney();

    }

    @When("^User enter details for create payment link$")
    public void enterpaymentlinkdetails() throws Throwable {
        String testcasename = CucumberStep.scenarioName;
        String SellingItem = xlsrdr.getData(testcasename, "SellingItem");
        String SellinItemAmount = xlsrdr.getData(testcasename, "SellingItemAmount");
        new PaymentLink().CreatePaymentLink(SellingItem, SellinItemAmount);
    }


    @Then("^Payment link successfully created$")
    public void paymentlinkcreation() throws Throwable {
        new PaymentLink().ValidatePaymentLink();
    }

    @And("^User selects Create Payment Link$")
    public void createpaymentlink() throws Throwable {
        String testcasename = CucumberStep.scenarioName;
        String EnterPayLahPwd = xlsrdr.getData(testcasename, "PayLahPWD");
        new PayLahHome().authentication(EnterPayLahPwd);
        new PayLahHome().PaymentLink();
    }

    @When("^User enter details for Request Money and submit$")
    public void requestMoneySubmit() throws Throwable {
        String testcasename = CucumberStep.scenarioName;
        String TotalAmt = xlsrdr.getData(testcasename, "TotalAmount");
        String MobileNum = xlsrdr.getData(testcasename, "MobileNumber");
        new RequestMoneyPage().requestMoneyfromAccount(TotalAmt, MobileNum);
    }

    @Then("^Request money successful message should be displayed$")
    public void verifyRequestMoney() {
        System.out.println("xyz");
    }

    @And("^User selects Topup link from Menu$")
    public void clickMenu() throws Throwable {
        String testcasename = CucumberStep.scenarioName;
        String Username = xlsrdr.getData(testcasename, "USERNAME");
        String Password = xlsrdr.getData(testcasename, "PASSWORD");
        String SecurePwd = xlsrdr.getData(testcasename, "SecurePwd");
        String EnterPayLahPwd = xlsrdr.getData(testcasename, "PayLahPWD");
        new PayLahHome().authentication(EnterPayLahPwd);
        new PayLahHome().topUpItemClick();
        new PayLahLoginViaSignUp().login(Username, Password, SecurePwd);

    }

    @When("^User enter details for Topup$")
    public void enterTopupDetails() throws Throwable {

        String testcasename = CucumberStep.scenarioName;
        String TopUpAmt = xlsrdr.getData(testcasename, "TopUpAmount");
        new TopUpMoney().topUpAccount(TopUpAmt);
    }

    @Then("^Topup successfully done$")
    public void validateTopUpSuccess() throws Throwable {
        new PaymentLink().ValidatePaymentLink();
    }

    @And("^User selects Send Money to my Account link from Menu$")
    public void clickSendToMoney() throws Throwable {
        String testcasename = CucumberStep.scenarioName;
        String EnterPayLahPwd = xlsrdr.getData(testcasename, "PayLahPWD");
        new PayLahHome().authentication(EnterPayLahPwd);
        new PayLahHome().sendToMoneyItemClick();
    }

    @When("^User enter details for Send Money$")
    public void enterSendToMoneyDetails() throws Throwable {

        String testcasename = CucumberStep.scenarioName;
        String SendMoneyToMyAccount = xlsrdr.getData(testcasename, "SendMoneyToMyAccount");
        new SendToMyAccount().sendMoneyToAccount(SendMoneyToMyAccount);
    }

    @Then("^Send Money successfully done$")
    public void validateSendToMoney() throws Throwable {
        new SendToMyAccount().validateSendToMoney();
    }


}

