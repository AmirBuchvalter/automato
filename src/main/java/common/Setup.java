package common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

class Setup {

    WebDriver setDriver(String osType) throws Exception {
        System.out.println("WebDriver is being setup");
        System.out.println("Driver is being set for the next system: " + osType);

        WebDriver driver;
        if (osType.contains("mac")) {
            driver = chromeSetupForOsx();

        } else if (osType.contains("win")) {
            driver = chromeSetupForWindows();
        } else {
            throw new Exception("Web driver is not supported for the current OS");
        }

        return driver;
    }

    private WebDriver chromeSetupForOsx() throws Exception {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        return new ChromeDriver();
    }

    private WebDriver chromeSetupForWindows() throws Exception {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        return new ChromeDriver();
    }
}
