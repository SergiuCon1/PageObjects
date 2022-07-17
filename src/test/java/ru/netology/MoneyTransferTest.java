package ru.netology;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.CardReplenishmentPage;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    @BeforeEach
    void shouldOpenPersonalAccount() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
//    var loginPage = open("http://localhost:9999", LoginPageV2.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        //Вызов объекта DashboardPage
        DashboardPage dashboardPage = new DashboardPage();
        //Жмем кнопку 'Пополнить' для первой карты. В итоге возвращается CardReplenishmentPage
        var cardReplenishmentPage = dashboardPage.replenishmentCard(0);
        //Вызов объекта CardReplenishmentPage
        CardReplenishmentPage cardReplenishment = new CardReplenishmentPage();
        //Заполняем поля и жмем 'Пополнить'
        cardReplenishment.filledData(5000, 1);
        //Проверяем баланс первой карты
        assertEquals(dashboardPage.getCardBalance(0), 15000);
        //Проверяем баланс второй карты
        assertEquals(dashboardPage.getCardBalance(1), 5000);
        //Жмем кнопку 'Пополнить' для второй карты. В итоге возвращается CardReplenishmentPage
        dashboardPage.replenishmentCard(1);
        //Очищаем поля для ввода новых данных(баг). При следующем переводе поля должны быть пустыми
        //TODO: После исправления бага необходимо будет удалить метод очистки
        cardReplenishment.clearData();
        //Заполняем поля и жмем 'Пополнить'
        cardReplenishment.filledData(5000, 0);
        //Проверяем баланс первой карты
        assertEquals(dashboardPage.getCardBalance(0), 10000);
        //Проверяем баланс второй карты
        assertEquals(dashboardPage.getCardBalance(1), 10000);
    }

    @Test
    void shouldValidationMessageWhenTransferMoreThanCurrentBalance() {
        DashboardPage dashboardPage = new DashboardPage();
        //Жмем кнопку 'Пополнить' для первой карты. В итоге возвращается CardReplenishmentPage
        var cardReplenishmentPage = dashboardPage.replenishmentCard(0);
        //Вызов объекта CardReplenishmentPage
        CardReplenishmentPage cardReplenishment = new CardReplenishmentPage();
        //Очищаем поля для ввода новых данных
        cardReplenishment.clearData();
        //Заполняем поля и жмем 'Пополнить'
        cardReplenishment.filledData(50000, 1);
        //Баг: должно появляться сообщение о нехвватке средств на счету
        //TODO: Добавить assertEquals, когда пофиксят баг
        cardReplenishment.getErrorMessage();
        cardReplenishment.cancelOperation();
        assertEquals(dashboardPage.getCardBalance(0), 10000);
        assertEquals(dashboardPage.getCardBalance(1), 10000);
    }
}
