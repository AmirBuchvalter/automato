package tests;

import common.Actions;
import org.openqa.selenium.WebElement;
import java.net.URL;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;


public class testsScenarios {

    public String searchAndNavigateScenario(Actions actions) throws Exception {
        actions.navigateToUtl(new URL("https://www.gamespot.com"));
        String mastheadSelector = "#masthead > nav > div.js-masthead-default.masthead-row.masthead-default";
        WebElement masthead = actions.findAnElementByCssSelector(mastheadSelector);
        String webPageTitle = actions.getWebPageTitle();
        try{
            assertNotNull(masthead);
            return "passed";
        }catch (AssertionError e)
        {
            return "failed. Reason ="  + e.getMessage();
        }

    }

    public String gamespotReviewsScenario(Actions actions) throws Exception {
        actions.navigateToUtl(new URL("https://www.gamespot.com/reviews/"), true);
        actions.takeScreenShootAndSaveToLocation("gamespotReviewsPage");
        List<WebElement> reviewLinks = actions.createElementsListByCssSelector("a.card-item__link");
        actions.clickOnTheFirstElementOfAlistByCssSelector(reviewLinks, "a.card-item__link");
        String pageTitle = actions.getWebPageTitle();
        actions.takeScreenShootAndSaveToLocation("gamespotReviewDetail");

        try {
            assertNotNull(pageTitle);
            return "passed - landed on: " + pageTitle;
        } catch (AssertionError e) {
            return "failed. Reason =" + e.getMessage();
        }
    }

}
