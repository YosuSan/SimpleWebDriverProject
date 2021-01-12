package selenium.test.amazon;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import selenium.core.Core;
import selenium.pages.amazon.AmazonHome;
import selenium.pages.amazon.AmazonProductPage;

public class Test_amazon extends Core {

	private AmazonHome home;
	private AmazonProductPage productPage;

	public Test_amazon() {
		home = new AmazonHome();
		productPage = new AmazonProductPage();
	}

	@BeforeSuite
	@Parameters({ "browser" })
	public void testParams(@Optional("chrome") String browser) {
		browser().putParam("browser", browser);
	}

	@Test(priority = 0, description = "Search a product, enter in the first result and select quantity", testName = "Search product")
	public void test_1_searchProduct() {

		home.searchProduct("raton");
		productPage.browseProduct("2");
	}

}
