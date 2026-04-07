package common;

import helpers.OperatingSystemHelper;
import org.openqa.selenium.WebDriver;

public abstract class Bootstrap {

    protected WebDriver driver;
    protected Actions actions;
    protected TearDown tearDown;

    protected void setUpBeforeScenario() throws Exception {
        System.out.println("Setting up before scenario");
        OperatingSystemHelper osHelpers = new OperatingSystemHelper();
        String osType = osHelpers.getCurrentOperatingSystem();
        driver = new Setup().setDriver(osType);
        tearDown = new TearDown();
        actions = new Actions(driver,tearDown);

    }
}
