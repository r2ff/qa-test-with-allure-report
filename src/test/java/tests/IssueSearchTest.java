package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;


public class IssueSearchTest extends TestBase {

    public static final String url = "https://github.com";
    public static final String repository = "selenide/selenide";
    public static final int number = 1474;

    //Селенид + листенер
    @Test
    void issueSearchTestSelenideOnly() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        open("https://github.com");
        $("[name=q]").setValue("selenide").pressEnter();
        $(By.linkText("selenide/selenide")).click();
        $(byText("Issues")).click();
        $(withText("#1474")).shouldBe(Condition.visible);
    }

    //Лямбда степы
    @Test
    @Owner("Alexey")
    @Feature("Issues")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "Github", url = "https://github.com")
    @DisplayName("Проверка по номеру issue")
    void issueSearchTestWithLambdaSteps() {
        step("Открываем страницу " + url, (s) -> {
            s.parameter("урл", url);
            open(url);
            steps.makeScreenshot();

        });
        step("Ищем репозиторий " + repository, (s) -> {
            s.parameter("репозиторий", repository);
            $("[name=q]").setValue(repository).pressEnter();
        });
        step("Выбираем необходимый репозиторий " + repository, (s) -> {
            s.parameter("репозиторий", repository);
            $(By.linkText("selenide/selenide")).click();
        });
        step("Переходим на вкладку Issue", () -> {
            $(byText("Issues")).click();
        });
        step("Проверяем issue c номером" + number, (s) -> {
            s.parameter("номер issue", number);
            $(withText("#" + number)).shouldBe(Condition.visible);
        });
    }

    private WebSteps steps = new WebSteps();

    //
    @Test
    void issueSearchWithAnnotatedSteps() {
        steps.openMainPage(url);
        steps.searchRepository(repository);
        steps.chooseRepository(repository);
        steps.openIssueTab();
        steps.shouldSeeIssueWithNumber(number);
        steps.makeScreenshot();
    }
}
