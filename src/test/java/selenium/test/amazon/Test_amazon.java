package selenium.test.amazon;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import selenium.core.SeleniumCore;
import selenium.pages.amazon.AmazonHome;
import selenium.pages.amazon.AmazonProductPage;
import webdriver.utils.AbstractUtils;

public class Test_amazon extends SeleniumCore {

	private AmazonHome home;
	private AmazonProductPage productPage;

	public Test_amazon() {
		home = new AmazonHome();
		productPage = new AmazonProductPage();
	}

	@BeforeSuite
	@Parameters({ "browser" })
	public void testParams(@Optional("chrome") String browser) {
		AbstractUtils.putParam("browser", browser);
		AbstractUtils.putParam("SuiteName", "Amazon");
	}

	@Test(priority = 0, description = "Search a product, enter in the first result and select quantity", testName = "Search product")
	public void test_1_searchProduct() {

		home.searchProduct("raton");
		productPage.browseProduct("2");
	}

}
