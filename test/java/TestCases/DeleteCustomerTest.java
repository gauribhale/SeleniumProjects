package TestCases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Base.BaseTest;
import Utilities.TestUtil;

public class DeleteCustomerTest extends BaseTest {

	@Test(dataProvider = "getData",enabled=false)
	public void deleteCustomer(String customer) throws InterruptedException {

		click("customerbtn_CSS");
		clearField("searchCust_CSS");
		type("searchCust_CSS", customer);
		Assert.assertTrue(isElementPresent("deleteCust_CSS"), "Customer name: "+customer+" do not exist");
		click("deleteCust_CSS");
		Assert.assertTrue(isAlertPresent(), "alert is not displayed");
		acceptAlert();
		
		
	}

	@DataProvider()
	public Object[][] getData() {

		return TestUtil.getData("Customer");

	}
}
