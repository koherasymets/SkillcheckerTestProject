package tech.Skillchecker.pages;

import tech.Skillchecker.context.TestContext;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {
    protected TestContext context;

    public BasePage(TestContext context) {
        this.context = context;
        PageFactory.initElements(context.driver, this);
    }
}