package common;

import com.google.common.collect.Iterables;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Actions {

    private final WebDriver driver;
    private final TearDown tearDown;
    private final int pageTimeoutLimit = 60;
    private final int elementTimeoutLimit = 60;

    @Inject
    public Actions(WebDriver driver, TearDown tearDown) {
        this.driver = driver;
        this.tearDown = tearDown;
    }

    public void dismissCookieConsentIfPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 3);
            WebElement acceptBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector(
                            "button[id*='accept'], button[aria-label*='Accept'], form[action*='consent'] button"
                    ))
            );
            acceptBtn.click();
            System.out.println("Dismissed cookie consent dialog");
        } catch (Exception e) {
            System.out.println("No cookie consent dialog found, continuing");
        }
    }

    public void navigateToUtl(URL url, Boolean printUrl) {
        try {
            driver.manage().timeouts().pageLoadTimeout(pageTimeoutLimit, TimeUnit.SECONDS);
            driver.get(String.valueOf(url));
            if (printUrl) {
                System.out.println("Current URL = " + url);
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
            driver.quit();
        }
    }

    public void navigateToUtl(URL url) {
        System.out.println("Opening browser and navigating to: " + url);
        navigateToUtl(url, false);
    }

    public List<WebElement> createElementsListByCssSelector(String cssPath) {
        System.out.println("Creating a list of page elements which match the next css path: " + cssPath);
        driver.manage().timeouts().implicitlyWait(pageTimeoutLimit, TimeUnit.SECONDS);
        List<WebElement> resultsList = driver.findElements(By.cssSelector(cssPath));
        System.out.println("Total URLs found in page count = " + resultsList.size());
        return resultsList;
    }

    public List<WebElement> createElementsListBySelectingParentElementCssSelector(String parentCssPath, String childsXpath) {
        WebElement parentElement = driver.findElement(By.cssSelector(parentCssPath));
        List<WebElement> menuItems = parentElement.findElements(By.xpath(childsXpath));
        System.out.println("Selected element list items:");
        menuItems.forEach(e -> System.out.println(e.getText()));
        return menuItems;
    }

    public WebElement findAnElementByCssSelector(String cssPath) {
        System.out.println("Trying to find an element with the next css path: " + cssPath);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssPath)));
    }

    public void clickOnTheFirstElementOfAlistByCssSelector(List<WebElement> list, String cssPath) throws Exception {
        System.out.println("Trying to click on the first element on the given list");
        WebDriverWait wait = new WebDriverWait(driver, elementTimeoutLimit);
        WebElement firstElement = wait.until(
                ExpectedConditions.elementToBeClickable(
                        Iterables.getFirst(list, driver.findElement(By.cssSelector(cssPath)))
                )
        );
        clickOnAnElement(firstElement);
    }

    public void clickOnAnElementOfAlistByXpath(List<WebElement> list, String itemText) throws Exception {
        System.out.println("Trying to click on an element from a given list");
        WebElement foundItem = null;
        for (WebElement item : list) {
            if (item.getText().equals(itemText)) {
                System.out.println("Found item with given text: " + itemText);
                foundItem = item;
                break;
            }
        }
        clickOnAnElement(foundItem);
    }

    private void clickOnAnElement(WebElement element) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, elementTimeoutLimit);
            element = wait.until(ExpectedConditions.elementToBeClickable(element));
            try {
                element.click();
            } catch (ElementClickInterceptedException e) {
                System.out.println("Normal click intercepted, using JavaScript click");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            }
        } catch (TimeoutException e) {
            tearDown.tearDownAfterScenario(driver);
            System.out.println("Web Element was not found: " + e.getMessage());
        }
    }

    public String getWebPageTitle() {
        System.out.println("Trying to get page title");
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        String pageTitle = driver.getTitle();
        System.out.println("Page title is: " + pageTitle);
        return pageTitle;
    }

    public WebElement getWebElementByCssSelector(String cssPath) {
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        return driver.findElement(By.cssSelector(cssPath));
    }

    public void takeScreenShootAndSaveToLocation(String imageName) throws IOException {
        System.out.println("Taking page screenshot and saving to default location by OS type");
        String screenShotFileOsPath = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator;

        new File(screenShotFileOsPath).mkdirs();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File(screenShotFileOsPath + imageName
                + "_"
                + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())
                + ".png"));
        System.out.println("Screenshot was saved to the next location: " + screenShotFileOsPath);
    }
}
