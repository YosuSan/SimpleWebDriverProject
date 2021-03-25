package selenium.test.dezlearn;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import selenium.core.Core;
import selenium.pages.dezlearn.MultiWindow;
import selenium.pages.dezlearn.TestSync;

public class Test_dezlearn extends Core {

	private TestSync testSync;
	private MultiWindow multiWindows;

	public Test_dezlearn() {
		testSync = new TestSync();
		multiWindows = new MultiWindow();
	}

	@DataProvider(name = "users")
	public Object[][] users() {
		return new Object[][] { { "First Name Ej", "Last Name Ej", "correo@email.com" } };
	}

	@BeforeSuite
	@Parameters({ "browser" })
	public void testParams(@Optional("chrome") String browser) {
		browser().putParam("browser", browser);
	}

	@Test(description = "Fill all data and wait for order number", testName = "Fill data", dataProvider = "users")
	public void test01_fillDataAndWait(Object data[]) {
		String firstName = data[0].toString();
		String lastName = data[1].toString();
		String email = data[2].toString();
		testSync.fillOrder(firstName, lastName, email);
	}
	
	@Test(description = "Open and close multiple windows", testName = "Multi windows")
	public void test02_openAndCloseMultiWindows() {
		multiWindows.checkAndCloseNewWindows();
	}

}
