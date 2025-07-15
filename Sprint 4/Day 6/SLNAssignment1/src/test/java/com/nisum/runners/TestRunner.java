package com.nisum.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Test Runner for Cucumber BDD tests
 * Following YAGNI principle - only includes necessary configuration
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.nisum.steps",
    plugin = {
        "pretty",
        "html:target/cucumber-reports/html-report",
        "json:target/cucumber-reports/json-report.json",
        "junit:target/cucumber-reports/junit-report.xml"
    },
    monochrome = true
)
public class TestRunner {
}
