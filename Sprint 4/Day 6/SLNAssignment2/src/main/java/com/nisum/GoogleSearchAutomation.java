package com.nisum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GoogleSearchAutomation {
    public static void main(String[] args) {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver-win64\\chromedriver.exe");

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Open Google
            driver.get("https://www.google.com");

            // Locate the search field and enter "BDD in Selenium"
            WebElement searchField = driver.findElement(By.name("q"));
            searchField.sendKeys("BDD in Selenium");

            // Click the search button
            WebElement searchButton = driver.findElement(By.name("btnK"));
            searchButton.click();

            // Wait for search results to appear
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement results = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#search")));

            // Verify that results appear
            if (results.isDisplayed()) {
                System.out.println("Search results are displayed.");
            } else {
                System.out.println("Search results are not displayed.");
            }
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
