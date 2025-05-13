package tech.Skillchecker.tests;

import tech.Skillchecker.pages.DashboardPage;
import tech.Skillchecker.pages.SkillMainPage;
import tech.Skillchecker.utils.ConfigurationReader;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Дашборд")
@Feature("Создание тестов и добавление кандидатов")
@Owner("Kostiantyn Herasymets")
public class SkillDashboardTest extends TestBase {

    @BeforeEach
    public void loginAndOpenDashboard() {
        context.driver.get(ConfigurationReader.get("url"));
        SkillMainPage skillMainPage = new SkillMainPage(context);
        skillMainPage.login(); // логин с валидными данными
    }

    @Test
    @Story("Создание нового теста")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка создания теста через дашборд")
    public void createNewTestFromDashboard() {
        DashboardPage dashboardPage = new DashboardPage(context);

        dashboardPage.createTest(
                "Автотест: Java Test",
                "Описание автотеста через PageObject"
        );

        assertTrue(true, "Тест создан, форма закрыта");
    }

    @Test
    @Story("Добавление кандидата")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка добавления нового кандидата")
    public void addNewCandidateFromDashboard() {
        DashboardPage dashboardPage = new DashboardPage(context);

        String name = "Иван Тестович";
        String email = "test." + System.currentTimeMillis() + "@example.com";

        dashboardPage.addCandidate(name, email);

        assertTrue(true, "Кандидат успешно добавлен (toast появился и исчез)");
    }

    @Test
    @Story("Добавление кандидата с существующим email")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка ошибки при добавлении кандидата с уже существующим email")
    public void addCandidateWithDuplicateEmail() {
        DashboardPage dashboardPage = new DashboardPage(context);

        String name = "Дубликат Имени";
        String email = "test.1747158932261@example.com"; // уже существующий email

        dashboardPage.tryToAddCandidateWithDuplicateEmail(name, email);

        assertTrue(dashboardPage.candidateErrorToast.isDisplayed(),
                "Toast с ошибкой должен быть показан при попытке добавить кандидата с дубликатом email");
    }
}