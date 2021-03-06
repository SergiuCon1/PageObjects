package ru.netology.data;

import lombok.Value;
import ru.netology.page.CardReplenishmentPage;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardNumber {
        private static String firstCard = "5559 0000 0000 0001";
        private static String secondCard = "5559 0000 0000 0002";
    }

    public static String getFirstCardNumber() {
        return CardNumber.firstCard;
    }

    public static String getSecondCardNumber() {
        return CardNumber.secondCard;
    }
}