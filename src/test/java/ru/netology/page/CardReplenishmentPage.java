package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import ru.netology.data.DataHelper;

import javax.xml.crypto.Data;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class CardReplenishmentPage {
    SelenideElement dashboard = $("h1.heading");
    private SelenideElement amountMoney = $("[data-test-id=\"amount\"] .input__control");
    private SelenideElement fromCard = $("[data-test-id=\"from\"] .input__control");
    private SelenideElement sendButton = $("[data-test-id=\"action-transfer\"] .button__content");
    SelenideElement cancelButton = $("[data-test-id=\"action-cancel\"] .button__content");
    private SelenideElement errorMessage = $("[data-test-id=\"error-notification\"] .notification__content");


    DataHelper data;

    public CardReplenishmentPage() {
        dashboard.shouldBe(visible);
    }

    public DashboardPage filledData(int amount, int indexCardFrom) {
        amountMoney.val(String.valueOf(amount));
        switch (indexCardFrom) {
            case 0:
                fromCard.val(data.getFirstCardNumber());
                break;
            case 1:
                fromCard.val(data.getSecondCardNumber());
                break;
        }
        sendButton.click();
        return new DashboardPage();
    }

    public void getErrorMessage() {
        String textMessage = "На вашем счету недостаточно средств";
        errorMessage.should(visible, Duration.ofSeconds(5));
        errorMessage.shouldHave(Condition.text(textMessage));
    }

    public DashboardPage cancelOperation() {
        cancelButton.click();
        return new DashboardPage();
    }

    //TODO: После исправления бага необходимо будет удалить метод очистки
    public void clearData() {
        amountMoney.sendKeys(Keys.CONTROL, "a", Keys.BACK_SPACE);
        fromCard.sendKeys(Keys.CONTROL, "a", Keys.BACK_SPACE);
    }
}
