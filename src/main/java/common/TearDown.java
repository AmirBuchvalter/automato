package common;

import org.openqa.selenium.WebDriver;

public class TearDown {

    public void tearDownAfterScenario(WebDriver driver) throws Exception {
        System.out.println("Closing browser");
        driver.quit();
    }

    public void tearDownAfterTestSuite(WebDriver driver) {
        System.out.println("Teardown had been done");
        driver.quit();
    }
}
