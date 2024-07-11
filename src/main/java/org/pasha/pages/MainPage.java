package org.pasha.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import lombok.Getter;

import static org.pasha.constants.Constants.EQUALS;
import static org.pasha.constants.Constants.GREATER;
import static org.pasha.constants.Constants.TIMEOUT;

public class MainPage {

    private final WebDriver driver;

    @FindBy(id = "coin_0")
    @Getter
    private WebElement button0;
    @FindBy(id = "coin_1")
    @Getter
    private WebElement button1;
    @FindBy(id = "coin_2")
    @Getter
    private WebElement button2;
    @FindBy(id = "coin_3")
    @Getter
    private WebElement button3;
    @FindBy(id = "coin_4")
    @Getter
    private WebElement button4;
    @FindBy(id = "coin_5")
    @Getter
    private WebElement button5;
    @FindBy(id = "coin_6")
    @Getter
    private WebElement button6;
    @FindBy(id = "coin_7")
    @Getter
    private WebElement button7;
    @FindBy(id = "coin_8")
    @Getter
    private WebElement button8;

    @FindBy(id = "left_0")
    @Getter
    private WebElement leftBowlInput0;
    @FindBy(id = "left_1")
    @Getter
    private WebElement leftBowlInput1;
    @FindBy(id = "left_2")
    @Getter
    private WebElement leftBowlInput2;
    @FindBy(id = "left_3")
    @Getter
    private WebElement leftBowlInput3;
    @FindBy(id = "left_4")
    @Getter
    private WebElement leftBowlInput4;
    @FindBy(id = "left_5")
    @Getter
    private WebElement leftBowlInput5;
    @FindBy(id = "left_6")
    @Getter
    private WebElement leftBowlInput6;
    @FindBy(id = "left_7")
    @Getter
    private WebElement leftBowlInput7;
    @FindBy(id = "left_8")
    @Getter
    private WebElement leftBowlInput8;

    @FindBy(id = "right_0")
    @Getter
    private WebElement rightBowlInput0;
    @FindBy(id = "right_1")
    @Getter
    private WebElement rightBowlInput1;
    @FindBy(id = "right_2")
    @Getter
    private WebElement rightBowlInput2;
    @FindBy(id = "right_3")
    @Getter
    private WebElement rightBowlInput3;
    @FindBy(id = "right_4")
    @Getter
    private WebElement rightBowlInput4;
    @FindBy(id = "right_5")
    @Getter
    private WebElement rightBowlInput5;
    @FindBy(id = "right_6")
    @Getter
    private WebElement rightBowlInput6;
    @FindBy(id = "right_7")
    @Getter
    private WebElement rightBowlInput7;
    @FindBy(id = "right_8")
    @Getter
    private WebElement rightBowlInput8;

    @FindBy(xpath = "//button[@id='reset' and text()='Reset']")
    @Getter
    private WebElement resetButton;
    @FindBy(id = "weigh")
    @Getter
    private WebElement weighButton;
    @FindBy(xpath = "//button[@id='reset' and @disabled]")
    @Getter
    private WebElement resultButton;

    @FindBy(xpath = "(//div[@class='status'])[1]")
    @Getter
    private WebElement leftGridLabel;
    @FindBy(xpath = "(//div[@class='status'])[2]")
    @Getter
    private WebElement rightGridLabel;

    @FindBy(xpath = "//div[@class='game-info']/div")
    @Getter
    private WebElement gameInfoLabel;
    @FindBy(xpath = "//div[@class='game-info']//li")
    @Getter
    private List<WebElement> gameInfoWeighings;

    @Getter
    By gameInfoWeighingsBy = By.xpath("//div[@class='game-info']//li");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void makeWeigh(Map<WebElement, String> left, Map<WebElement, String> right, String leftDigit, String rightDigit,
            WebElement leftDigitButton, WebElement rightDigitButton) {
        resetButton.click();
        left.clear();
        left.put(leftBowlInput0, leftDigit);
        right.clear();
        left.put(rightBowlInput0, rightDigit);
        String r = makeWeigh(left, right);
        if (EQUALS.equalsIgnoreCase(r)) {
            Assert.fail("Something is wrong! Bars are equals!");
        } else if (GREATER.equalsIgnoreCase(r)) {
            validateFinalResult(rightDigitButton);
        } else {
            validateFinalResult(leftDigitButton);
        }
    }

    public String makeWeigh(Map<WebElement, String> digitsToLeft, Map<WebElement, String> digitsToRight) {
        resetButton.click();
        digitsToLeft.forEach(WebElement::sendKeys);
        digitsToRight.forEach(WebElement::sendKeys);
        weighButton.click();
        waitForNewWeighResultAppears(getNumberOfExistingWeighResults());
        return resultButton.getText();
    }

    public void waitForNewWeighResultAppears(int numberOfExistingWeighResults) {
        try {
            ExpectedCondition<Boolean> condition = driver -> {
                List<WebElement> webElements = new ArrayList<>();
                try {
                    webElements = driver.findElements(gameInfoWeighingsBy);
                } catch (Exception ignored) {
                }
                return webElements.size() > numberOfExistingWeighResults;
            };
            WebDriverWait waitForText = new WebDriverWait(this.driver, Duration.ofSeconds(TIMEOUT));
            waitForText.ignoring(NoSuchElementException.class);
            waitForText.until(condition);
        } catch (Exception ignored) {
        }
    }

    public int getNumberOfExistingWeighResults() {
        try {
            return driver.findElements(gameInfoWeighingsBy).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void validateFinalResult(WebElement button) {
        Alert alert = null;
        try {
            button.click();
            alert = driver.switchTo().alert();
            Assert.assertEquals(alert.getText(), "Yay! You find it!");
        } finally {
            if (alert != null) {
                alert.accept();
            }
        }
    }
}