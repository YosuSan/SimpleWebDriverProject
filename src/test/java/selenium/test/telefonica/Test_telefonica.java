package selenium.test.telefonica;


import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import selenium.core.SeleniumCore;
import selenium.pages.telefonica.TelefonicaHome;
import selenium.utils.UtilsSelenium;
import webdriver.utils.AbstractUtils;

public class Test_telefonica extends SeleniumCore {
	
	private TelefonicaHome home;
	
	public Test_telefonica() {
		home = new TelefonicaHome();
	}
	
	@BeforeSuite
	@Parameters({ "browser" })
	public void testParams(@Optional("chrome") String browser) {
		UtilsSelenium.putParam("browser", browser);
		AbstractUtils.putParam("SuiteName", "Telefonica");
	}
	
	@Test(description = "Go to home page and get all tags 'a' with href in domain", testName = "Get hrefs on domain")
	public void test01_getHrefs() {
		home.getAllTagsOnDomain();
	}

}
