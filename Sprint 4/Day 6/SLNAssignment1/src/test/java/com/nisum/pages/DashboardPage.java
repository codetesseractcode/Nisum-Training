package com.nisum.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

/**
 * Page Object Model for Dashboard Page
 * Following Single Responsibility Principle - handles only dashboard page interactions
 */
public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "welcomeMessage")
    private WebElement welcomeMessage;

    @FindBy(className = "dashboard-container")
    private WebElement dashboardContainer;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    /**
     * Check if currently on dashboard page
     * @return true if on dashboard page
     */
    public boolean isOnDashboardPage() {
        return driver.getCurrentUrl().contains("dashboard.html");
    }

    /**
     * Check if welcome message is displayed
     * @return true if welcome message is visible
     */
    public boolean isWelcomeMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(welcomeMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get welcome message text
     * @return welcome message text
     */
    public String getWelcomeMessage() {
        if (isWelcomeMessageDisplayed()) {
            return welcomeMessage.getText();
        }
        return "";
    }

    /**
     * Wait for dashboard to load completely
     * @return true if dashboard loaded successfully
     */
    public boolean waitForDashboardToLoad() {
        try {
            wait.until(ExpectedConditions.visibilityOf(dashboardContainer));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
