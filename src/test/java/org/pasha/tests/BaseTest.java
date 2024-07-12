package org.pasha.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import lombok.Getter;

import static org.pasha.constants.Constants.BASE_URL;

public class BaseTest {

    @Getter
    private WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
