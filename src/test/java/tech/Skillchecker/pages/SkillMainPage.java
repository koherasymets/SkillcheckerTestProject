package tech.Skillchecker.pages;

import tech.Skillchecker.context.TestContext;
import tech.Skillchecker.utils.ConfigurationReader;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import io.qameta.allure.Step;

public class SkillMainPage extends BasePage {

    public SkillMainPage(TestContext context) {
        super(context);
    }

    @FindBy(name = "username")
    public WebElement usernameInput;

    @FindBy(name = "password")
    public WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    public WebElement loginButton;

    @FindBy(css = "div[role='alert']") //
    public WebElement errorToast;

    @Step("Вход в систему с логином из конфигурации")
    public DashboardPage login() {
        return login(ConfigurationReader.get("userName"), ConfigurationReader.get("userPassword"));
    }

    @Step("Вход в систему с логином: {username}, паролем: {password}")
    public DashboardPage login(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
        return new DashboardPage(context);
    }

    public String getLoginErrorToastText() {
        return errorToast.getText();
    }
}