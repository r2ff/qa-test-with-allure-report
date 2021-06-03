package tests;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class WebSteps {

    @Step("Открываем страницу {url}")
    public void openMainPage(String url) {
        open(url);
    }

    @Step("Ищем репозиторий {repository}")
    public void searchRepository(String repository) {
        $("[name=q]").setValue(repository).pressEnter();
    }

    @Step("Выбираем необходимый репозиторий {repository}")
    public void chooseRepository(String repository) {
        $(By.linkText(repository)).click();
    }

    @Step("Переходим на вкладку Issue")
    public void openIssueTab() {
        $(byText("Issues")).click();
    }

    @Step("Проверяем issue c номером {number}")
    public void shouldSeeIssueWithNumber(int number)  {
        $(withText("#" + number)).shouldBe(Condition.visible);
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] makeScreenshot() {
        return screenshot(OutputType.BYTES);
    }
}
