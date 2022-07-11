package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.netology.data.DataHelper;

import javax.xml.crypto.Data;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class CardReplenishmentPage {
    SelenideElement dashboard = $("h1.heading");
    private SelenideElement amountMoney = $("[data-test-id=\"amount\"] .input__control");
    private SelenideElement fromCard = $("[data-test-id=\"from\"] .input__control");
    private SelenideElement sendButton = $("[data-test-id=\"action-transfer\"] .button__content");
    SelenideElement cancelButton = $("[data-test-id=\"action-cancel\"] .button__content");

    DataHelper.CardNumber data;

    public CardReplenishmentPage() {
        dashboard.shouldBe(visible);
    }

    public DashboardPage filledData(int amount, int indexCardFrom) {
        amountMoney.val(String.valueOf(amount));
        fromCard.val(data.getCardNumber(indexCardFrom));
        sendButton.click();
        return new DashboardPage();
    }
}
