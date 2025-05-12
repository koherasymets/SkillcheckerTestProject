package tech.Skillchecker.tests;

import tech.Skillchecker.pages.DashboardPage;
import tech.Skillchecker.pages.SkillMainPage;
import tech.Skillchecker.utils.ConfigurationReader;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Авторизация")
@Feature("Логин")
@Story("Успешный вход с валидными данными")
@Owner("Kostiantyn Herasymets")
@Severity(SeverityLevel.CRITICAL)
@DisplayName("Проверка успешного входа в систему")
public class SkillUserLoginTest extends TestBase {

    @BeforeEach
    public void openLoginPage() {
        context.driver.get(ConfigurationReader.get("url"));
    }

    @Test
    @Description("Проверка успешного входа в систему с валидными данными")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Успешный логин — пользователь попадает в Панель управления")
    public void LoginTest() {
        SkillMainPage skillMainPage = new SkillMainPage(context);
        DashboardPage dashboardPage = skillMainPage.login();

        WebElement headerElement = context.wait.until(
                ExpectedConditions.visibilityOf(dashboardPage.header)
        );
        String headerText = headerElement.getText();
        assertEquals("Панель управления", headerText);
    }

    @Test
    @Description("Проверка сообщения об ошибке при неверном пароле")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Неверный логин/пароль — система выдаёт уведомление")
    public void loginWithWrongPassword() {
        SkillMainPage skillMainPage = new SkillMainPage(context);
        skillMainPage.login("admin", "wrongpass123");

        WebElement errorToast = context.wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("li[role='status'][data-state='open']")
        ));

        String errorToastText = errorToast.getText();

        assertTrue(errorToastText.contains("Ошибка входа"), "Не найден заголовок ошибки");
        assertTrue(errorToastText.contains("401"), "Код ошибки не отображается");
        assertTrue(errorToastText.contains("Invalid credentials"), "Текст 'Invalid credentials' не найден");
    }

    @Test
    @Description("Проверка ошибки при пустом логине")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Пустой логин — появляется сообщение 'Имя пользователя обязательно'")
    public void loginWithEmptyUsername() {
        SkillMainPage skillMainPage = new SkillMainPage(context);
        skillMainPage.login("", "admin123");

        WebElement usernameError = context.wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'text-destructive') and contains(text(), 'Имя пользователя обязательно')]")
        ));

        assertTrue(usernameError.isDisplayed(), "Сообщение о том, что логин обязателен, не отображается");
        assertEquals("Имя пользователя обязательно", usernameError.getText().trim());
    }

    @Test
    @Description("Проверка ошибки при пустом пароле")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Пустой пароль — появляется сообщение 'Пароль обязателен'")
    public void loginWithEmptyPassword() {
        SkillMainPage skillMainPage = new SkillMainPage(context);
        skillMainPage.login("admin", "");

        WebElement passwordError = context.wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'text-destructive') and contains(text(), 'Пароль обязателен')]")
        ));

        assertTrue(passwordError.isDisplayed(), "Сообщение о том, что пароль обязателен, не отображается");
        assertEquals("Пароль обязателен", passwordError.getText().trim());
    }
}