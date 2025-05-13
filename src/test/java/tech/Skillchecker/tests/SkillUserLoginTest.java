package tech.Skillchecker.tests;

import tech.Skillchecker.pages.DashboardPage;
import tech.Skillchecker.pages.SkillMainPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Авторизация")
@Feature("Логин")
@Owner("Kostiantyn Herasymets")
public class SkillUserLoginTest extends TestBase {

    @Test
    @Story("Успешный вход с валидными данными")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка успешного входа в систему")
    @Description("Авторизация с валидными данными — пользователь попадает в панель управления")
    public void LoginTest() {
        SkillMainPage skillMainPage = new SkillMainPage(context);
        DashboardPage dashboardPage = skillMainPage.login();

        WebElement headerElement = context.wait.until(
                ExpectedConditions.visibilityOf(dashboardPage.header)
        );
        String headerText = headerElement.getText();

        assertTrue("Панель управления".equals(headerText),
                "Ожидался текст 'Панель управления', но был: " + headerText);
    }

    @Test
    @Story("Ошибки при вводе неверных данных")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Неверный логин/пароль — появляется сообщение об ошибке под полем")
    @Description("Проверка появления сообщения об ошибке при вводе неверного пароля")
    public void loginWithWrongPassword() {
        SkillMainPage skillMainPage = new SkillMainPage(context);

        skillMainPage.login("admin", "wrongpass123");

        WebElement errorMessage = context.wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'text-destructive') and contains(text(), 'Некорректная')]")
        ));

        assertTrue(errorMessage.isDisplayed(), "Сообщение об ошибке не отображается");
        assertTrue(
                errorMessage.getText().contains("Некорректная электронная почта")
                        || errorMessage.getText().contains("Invalid credentials"),
                "Ожидалось сообщение об ошибке, но текст был: " + errorMessage.getText()
        );
    }

    @Test
    @Story("Ошибки при вводе неверных данных")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Пустой логин — появляется сообщение об ошибке под полем")
    @Description("Проверка появления сообщения 'Некорректная электронная почта' при пустом логине")
    public void loginWithEmptyUsername() {
        SkillMainPage skillMainPage = new SkillMainPage(context);

        skillMainPage.login("", "admin123");

        WebElement usernameError = context.wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'text-destructive') and contains(text(), 'Некорректная')]")
        ));

        assertTrue(usernameError.isDisplayed(), "Сообщение об ошибке под логином не отображается");
        assertTrue("Некорректная электронная почта".equals(usernameError.getText().trim()),
                "Ожидался текст 'Некорректная электронная почта', но был: " + usernameError.getText().trim());
    }

    @Test
    @Story("Ошибки при вводе неверных данных")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Пустой пароль — появляется сообщение 'Пароль обязателен'")
    @Description("Проверка появления сообщения об ошибке при пустом пароле")
    public void loginWithEmptyPassword() {
        SkillMainPage skillMainPage = new SkillMainPage(context);

        skillMainPage.login("admin", "");

        WebElement passwordError = context.wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'text-destructive') and contains(text(), 'Пароль обязателен')]")
        ));

        assertTrue(passwordError.isDisplayed(), "Сообщение о том, что пароль обязателен, не отображается");
        assertTrue("Пароль обязателен".equals(passwordError.getText().trim()),
                "Ожидался текст 'Пароль обязателен', но был: " + passwordError.getText().trim());
    }
}