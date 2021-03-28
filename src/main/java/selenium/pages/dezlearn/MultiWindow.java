package selenium.pages.dezlearn;

import org.openqa.selenium.By;

import selenium.core.Core;

public class MultiWindow {

	private void goPage() {
		Core.browser().goToUrl(Urls.MULTIPLE_TABS.getUrl());
	}

	By locatorLaunchWindows = By.id("u_7_8");

	public void checkAndCloseNewWindows() {
		goPage();

		Core.browser().waitForVisibility(locatorLaunchWindows, 5);
		Core.browser().click(locatorLaunchWindows);

		Core.browser().sleepSeconds(2);

		while (Core.browser().getWindows().size() > 1) {
			Core.browser().switchWindowFromMain();
			Core.browser().setLog("Switched to window => " + Core.browser().driver().getWindowHandle());
			String currentUrl = Core.browser().driver().getCurrentUrl();
			Core.browser().setLog("New tab in url '" + currentUrl + "' will be closed in 1 seconds");
			Core.browser().sleepSeconds(1);
			Core.browser().driver().close();
			Core.browser().switchToMainWindow();
		}

	}

}
