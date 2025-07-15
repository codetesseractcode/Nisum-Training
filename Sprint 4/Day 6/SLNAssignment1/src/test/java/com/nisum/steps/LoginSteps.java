package com.nisum.steps;

import com.nisum.pages.DashboardPage;
import com.nisum.pages.LoginPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

/**
 * Step definitions for login feature
 * Following Single Responsibility Principle - handles only login-related steps
 */
public class LoginSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode for CI/CD
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);

        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("the user is on the login page")
    public void theUserIsOnTheLoginPage() {
        String loginPagePath = "file://" + System.getProperty("user.dir") +
                              "/src/test/resources/login.html";
        driver.get(loginPagePath);
        Assert.assertTrue("User should be on login page", loginPage.isOnLoginPage());
    }

    @When("the user enters valid username {string} and password {string}")
    public void theUserEntersValidUsernameAndPassword(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("the user enters username {string} and password {string}")
    public void theUserEntersUsernameAndPassword(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @And("the user clicks the login button")
    public void theUserClicksTheLoginButton() {
        loginPage.clickLoginButton();
    }

    @Then("the user should be redirected to the dashboard")
    public void theUserShouldBeRedirectedToTheDashboard() {
        // Wait a moment for redirect
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Assert.assertTrue("User should be redirected to dashboard",
                         dashboardPage.isOnDashboardPage());
    }

    @And("the dashboard should display welcome message")
    public void theDashboardShouldDisplayWelcomeMessage() {
        Assert.assertTrue("Welcome message should be displayed",
                         dashboardPage.waitForDashboardToLoad());
        Assert.assertTrue("Welcome message should be visible",
                         dashboardPage.isWelcomeMessageDisplayed());
    }

    @Then("the user should see error message {string}")
    public void theUserShouldSeeErrorMessage(String expectedErrorMessage) {
        Assert.assertTrue("Error message should be displayed",
                         loginPage.isErrorMessageDisplayed());
        String actualErrorMessage = loginPage.getErrorMessage();
        Assert.assertEquals("Error message should match expected message",
                           expectedErrorMessage, actualErrorMessage);
    }

    @And("the user should remain on the login page")
    public void theUserShouldRemainOnTheLoginPage() {
        Assert.assertTrue("User should remain on login page",
                         loginPage.isOnLoginPage());
    }
}
