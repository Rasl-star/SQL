package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public DashboardPage validVerify(DataHelper.VerificationCode code) {
        return validVerify(code.getCode());
    }

    public DashboardPage validVerify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        return new DashboardPage();
    }

    public void verifyVerificationPageVisibility() {
        codeField.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void verifyErrorNotificationVisibility(String expectedText) {
        errorNotification.shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text(expectedText));

    }

    public void verify(String code) {
    }
}