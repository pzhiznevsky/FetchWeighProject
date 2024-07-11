# FetchWeighProject

The project is a test project to demonstrate abilities of selenium web driver features.

Requirements: Java 21

Tech stack: Selenium WebDriver 4, TestNG, Java 21, Lombok

Test Design: Page Object pattern

Instructions to run:

Case 1: Using OS Maven
1. Open command prompt to the project
2. Run "mvn clean test"
3. Check test report in /target/surefire-reports/emailable-report/html

Case 2: Using Maven from your IDE (Intellij IDEA)
1. Open the project in your IDE
2. Open Maven tab on the right side
3. Execute Maven Goal: "mvn clean test"
4. Check test report in /target/surefire-reports/emailable-report/html

Case 3: Using TestNG from your IDE (Intellij IDEA)
1. Open the project in your IDE
2. Do right-click on WeighTest.java or testng.xml
3. Select Run 'WeighTest'
4. Check test report in /target/surefire-reports/emailable-report/html


NOTE: Added ability to run the test as many times as wanted. Just set invocationCount parameter in WeighTest.java. Default value is 1.