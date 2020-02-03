package TestCases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Base.BaseTest;
import Utilities.TestUtil;

public class OpenAccountTest extends BaseTest {
	
	@Test(dataProvider="getData")
	public void openAccount(String customer,String currency) throws InterruptedException {
		click("openAccbtn_CSS");
		select("custName_CSS",customer);
		select("currency_CSS",currency);
		click("processbtn_CSS");
		acceptAlert();
	}
	
	@DataProvider()
	public Object[][] getData() {
				
		return TestUtil.getData("OpenAccount");
		
	}
}
