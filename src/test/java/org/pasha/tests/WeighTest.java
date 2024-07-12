package org.pasha.tests;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.pasha.pages.MainPage;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import static org.pasha.constants.Constants.DIGIT_0;
import static org.pasha.constants.Constants.DIGIT_1;
import static org.pasha.constants.Constants.DIGIT_2;
import static org.pasha.constants.Constants.DIGIT_3;
import static org.pasha.constants.Constants.DIGIT_4;
import static org.pasha.constants.Constants.DIGIT_5;
import static org.pasha.constants.Constants.DIGIT_6;
import static org.pasha.constants.Constants.DIGIT_7;
import static org.pasha.constants.Constants.EQUALS;
import static org.pasha.constants.Constants.GREATER;
import static org.pasha.constants.Constants.LESS;

public class WeighTest extends BaseTest {

    @Test(invocationCount = 1)
    public void testWeighBars() {
        getDriver().navigate().refresh();
        MainPage page = new MainPage(getDriver());
        Map<WebElement, String> left = new HashMap<>();
        left.put(page.getLeftBowlInput0(), DIGIT_0);
        left.put(page.getLeftBowlInput1(), DIGIT_1);
        Map<WebElement, String> right = new HashMap<>();
        right.put(page.getRightBowlInput0(), DIGIT_2);
        right.put(page.getRightBowlInput1(), DIGIT_3);

        String r = page.makeWeigh(left, right);
        String errorMessage = "Unexpected Result returned! Result: " + r;

        switch (r) {
            case EQUALS -> {
                left.clear();
                left.put(page.getLeftBowlInput0(), DIGIT_4);
                left.put(page.getLeftBowlInput1(), DIGIT_5);
                right.clear();
                right.put(page.getRightBowlInput0(), DIGIT_6);
                right.put(page.getRightBowlInput1(), DIGIT_7);
                r = page.makeWeigh(left, right);
                switch (r) {
                    case EQUALS -> page.validateFinalResult(page.getButton8());
                    case GREATER -> page.makeWeigh(left, right, DIGIT_6, DIGIT_7, page.getButton6(), page.getButton7());
                    case LESS -> page.makeWeigh(left, right, DIGIT_4, DIGIT_5, page.getButton4(), page.getButton5());
                    default -> Assert.fail(errorMessage);
                }
            }
            case GREATER -> page.makeWeigh(left, right, DIGIT_2, DIGIT_3, page.getButton2(), page.getButton3());
            case LESS -> page.makeWeigh(left, right, DIGIT_0, DIGIT_1, page.getButton0(), page.getButton1());
            default -> Assert.fail(errorMessage);
        }

        Reporter.log(page.getAllWeighResults(), true);
    }
}
