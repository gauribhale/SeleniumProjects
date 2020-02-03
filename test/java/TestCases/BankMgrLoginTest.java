package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import Base.BaseTest;

public class BankMgrLoginTest extends BaseTest {

	@Test
	public void loginAsBankMgr() {
		click("mgrbtn_CSS");
		Assert.assertTrue(isElementPresent("addCustbtn_CSS"), "add customer button is not found");

	}
}
