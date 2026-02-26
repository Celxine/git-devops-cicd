package com.devops;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AppTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
    WebDriverManager.chromedriver().setup();
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    driver = new ChromeDriver(options);
    driver.manage().window().maximize();
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    wait = new WebDriverWait(driver, Duration.ofSeconds(30));
}

    
@Test
public void testSortByNameAscending() {
    driver.get("https://practicesoftwaretesting.com/#/");

    // Wait for products to load initially
    WebElement firstProduct = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='product-name']"))
    );

    // Select A-Z sort
    Select select = new Select(wait.until(
        ExpectedConditions.elementToBeClickable(By.cssSelector("[data-test='sort']"))
    ));
    select.selectByValue("name,asc");

    // Wait for the old product to go stale (page reloaded with new results)
    wait.until(ExpectedConditions.stalenessOf(firstProduct));

    // Now grab the freshly sorted product names
    List<WebElement> productElements = wait.until(
        ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[data-test='product-name']"))
    );

    List<String> names = new ArrayList<>();
    for (WebElement product : productElements) {
        names.add(product.getText().toLowerCase());
    }

    for (int i = 0; i < names.size() - 1; i++) {
        Assertions.assertTrue(
            names.get(i).compareTo(names.get(i + 1)) <= 0,
            "Sort A-Z failed: '" + names.get(i) + "' appeared before '" + names.get(i + 1) + "'"
        );
    }
}

@Test
public void testSortByNameDescending() {
    driver.get("https://practicesoftwaretesting.com/#/");

    // Wait for products to load initially
    WebElement firstProduct = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='product-name']"))
    );

    // Select Z-A sort
    Select select = new Select(wait.until(
        ExpectedConditions.elementToBeClickable(By.cssSelector("[data-test='sort']"))
    ));
    select.selectByValue("name,desc");

    // Wait for the old product to go stale (page reloaded with new results)
    wait.until(ExpectedConditions.stalenessOf(firstProduct));

    // Now grab the freshly sorted product names
    List<WebElement> productElements = wait.until(
        ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[data-test='product-name']"))
    );

    List<String> names = new ArrayList<>();
    for (WebElement product : productElements) {
        names.add(product.getText().toLowerCase());
    }

    for (int i = 0; i < names.size() - 1; i++) {
        Assertions.assertTrue(
            names.get(i).compareTo(names.get(i + 1)) >= 0,
            "Sort Z-A failed: '" + names.get(i) + "' appeared before '" + names.get(i + 1) + "'"
        );
    }
}
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}