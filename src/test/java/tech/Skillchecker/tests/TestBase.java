package tech.Skillchecker.tests;

import tech.Skillchecker.context.TestContext;
import tech.Skillchecker.utils.ConfigurationReader;
import tech.Skillchecker.utils.DriverFactory;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;

import java.io.ByteArrayInputStream;
import java.time.Duration;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;

public class TestBase {
    protected TestContext context;

    @BeforeEach
    public void beforeEach() {
        context = new TestContext();
        context.driver = DriverFactory.get();
        context.wait = new WebDriverWait(context.driver, Duration.ofSeconds(Long.parseLong(ConfigurationReader.get("timeout"))));
        context.actions = new Actions(context.driver);
        context.js = (JavascriptExecutor) context.driver;
    }

    @AfterEach
    public void afterEach() {
        TakesScreenshot takesScreenshot = (TakesScreenshot) context.driver;
        byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment("Скриншот", new ByteArrayInputStream(screenshot));
        if (context.driver != null) {
            context.driver.quit();
        }
    }
}