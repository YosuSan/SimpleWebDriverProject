package selenium.pages.dezlearn;

import org.openqa.selenium.By;

import selenium.core.SeleniumCore;

public class MultiWindow {

	private void goPage() {
		SeleniumCore.browser().goToUrl(Urls.MULTIPLE_TABS.getUrl());
	}

	By locatorLaunchWindows = By.id("u_7_8");

	public void checkAndCloseNewWindows() {
		goPage();

		SeleniumCore.browser().waitForVisibility(locatorLaunchWindows, 5);
		SeleniumCore.browser().click(locatorLaunchWindows);

		SeleniumCore.browser().sleepSeconds(2);

		while (SeleniumCore.browser().getWindows().size() > 1) {
			SeleniumCore.browser().switchWindowFromMain();
			SeleniumCore.browser().setLogInfo("Switched to window => " + SeleniumCore.browser().driver().getWindowHandle());
			String currentUrl = SeleniumCore.browser().driver().getCurrentUrl();
			SeleniumCore.browser().setLogInfo("New tab in url '" + currentUrl + "' will be closed in 1 seconds");
			SeleniumCore.browser().sleepSeconds(1);
			SeleniumCore.browser().driver().close();
			SeleniumCore.browser().switchToMainWindow();
		}

	}

}
