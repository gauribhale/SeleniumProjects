package TestCases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Base.BaseTest;
import Utilities.TestUtil;

public class AddCustomerTest extends BaseTest {

	@Test(dataProvider = "getData")
	public void addCustomer(String firstName, String lastName, String postCode) throws InterruptedException {
		click("addCustbtn_CSS");
		type("firstName_CSS", firstName);
		type("lastName_CSS", lastName);
		type("postCode_CSS", postCode);
		click("sbmtCustbtn_CSS");
		isAlertPresent();
		acceptAlert();

	}

	@DataProvider()
	public Object[][] getData() {

		return TestUtil.getData("AddCustomer");

	}
}
