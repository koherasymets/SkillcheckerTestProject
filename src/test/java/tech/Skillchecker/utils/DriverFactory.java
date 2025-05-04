package tech.Skillchecker.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    private DriverFactory() {}

    public static WebDriver get() {
        String browser = ConfigurationReader.get("browser").toLowerCase();

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            default:
                throw new RuntimeException("Браузер не поддерживается: " + browser);
        }
    }
}