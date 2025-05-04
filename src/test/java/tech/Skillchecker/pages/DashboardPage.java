package tech.Skillchecker.pages;

import tech.Skillchecker.context.TestContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage {

    public DashboardPage(TestContext context) {
        super(context);
    }

    @FindBy(xpath = "//h1[contains(text(), 'Панель управления')]")
    public WebElement header;
}