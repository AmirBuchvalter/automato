package com.company;

import common.Bootstrap;
import tests.testsScenarios;

import java.util.HashMap;
import java.util.Map;

class TestManager extends Bootstrap {

    private testsScenarios tests = new testsScenarios();
    private Map<String, String> map = new HashMap<>();

    void RunTestsSuit() throws Exception {
        runScenarioGamespotMasthead();
        runScenarioGamespotReviews();

        String newLine = System.getProperty("line.separator");
        System.out.println("Tests Results:" + newLine);
        for (String testName : map.keySet()) {
            String testStatus = map.get(testName);
            System.out.println(testName + ": result = " + testStatus);
        }

        tearDown.tearDownAfterTestSuite(driver);
    }

    private void runScenarioGamespotMasthead() throws Exception {
        System.out.println("--------- Running ScenarioGamespotMasthead ---------");
        setUpBeforeScenario();
        String testResult = tests.searchAndNavigateScenario(actions);
        map.put("ScenarioGamespotMasthead", testResult);
        tearDown.tearDownAfterScenario(driver);
    }

    private void runScenarioGamespotReviews() throws Exception {
        System.out.println("--------- Running ScenarioGamespotReviews ---------");
        setUpBeforeScenario();
        String testResult = tests.gamespotReviewsScenario(actions);
        map.put("ScenarioGamespotReviews", testResult);
        tearDown.tearDownAfterScenario(driver);
    }


}
