import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDeliveryTest {

    @BeforeEach
    void setUpAll() {
        Configuration.timeout = 10_000;
        open("http://localhost:9999");
    }

    @Test
    void shouldPost4Days() {
        int daysToAdd = 4;
        String plannedDate = DataGenerator.generateDate(daysToAdd);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Грозный").pressTab();
        $("[data-test-id=date] .input__control").doubleClick();
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(plannedDate).pressTab();
        $("[data-test-id=name] input").setValue("Ярослав Черников").pressTab();
        $("[data-test-id=phone] input").setValue("+12345678901").pressTab();
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + plannedDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    void shouldPost3Days() {
        int daysToAdd = 3;
        String plannedDate = DataGenerator.generateDate(daysToAdd);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Грозный").pressTab();
        $("[data-test-id=date] .input__control").doubleClick();
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(plannedDate).pressTab();
        $("[data-test-id=name] input").setValue("Ярослав Черников").pressTab();
        $("[data-test-id=phone] input").setValue("+12345678901").pressTab();
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + plannedDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    void shouldNotPostInvalidDate() {
        int daysToAdd = 2;
        String plannedDate = DataGenerator.generateDate(daysToAdd);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Грозный").pressTab();
        $("[data-test-id=date] .input__control").doubleClick();
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(plannedDate).pressTab();
        $("[data-test-id=name] input").setValue("Ярослав Черников").pressTab();
        $("[data-test-id=phone] input").setValue("+12345678901").pressTab();
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $("[data-test-id=date] .input_invalid").shouldBe(visible).shouldHave(text("дат"));
    }

    @Test
    void shouldNotPostInvalidCity() {
        int daysToAdd = 4;
        String plannedDate = DataGenerator.generateDate(daysToAdd);
        $("[data-test-id=city] input").setValue("Мирный").pressTab();
        $("[data-test-id=date] .input__control").doubleClick();
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(plannedDate).pressTab();
        $("[data-test-id=name] input").setValue("Ярослав Черников").pressTab();
        $("[data-test-id=phone] input").setValue("+12345678901").pressTab();
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $("[data-test-id=city].input_invalid").shouldBe(visible).shouldHave(text("город"));
    }

    @Test
    void shouldNotPostInvalidName() {
        int daysToAdd = 4;
        String plannedDate = DataGenerator.generateDate(daysToAdd);
        $("[data-test-id=city] input").setValue("Грозный").pressTab();
        $("[data-test-id=date] .input__control").doubleClick();
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(plannedDate).pressTab();
        $("[data-test-id=name] input").setValue("Ivan Ivanov").pressTab();
        $("[data-test-id=phone] input").setValue("+12345678901").pressTab();
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $("[data-test-id=name].input_invalid").shouldBe(visible).shouldHave(text("русск"));
    }

    @Test
    void shouldNotPostInvalidPhone() {
        int daysToAdd = 4;
        String plannedDate = DataGenerator.generateDate(daysToAdd);
        $("[data-test-id=city] input").setValue("Грозный").pressTab();
        $("[data-test-id=date] .input__control").doubleClick();
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(plannedDate).pressTab();
        $("[data-test-id=name] input").setValue("Ярослав Черников").pressTab();
        $("[data-test-id=phone] input").setValue("+1234567890").pressTab();
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $("[data-test-id=phone].input_invalid").shouldBe(visible).shouldHave(text("телефон"));
    }

    @Test
    void shouldNotPostNoAgreement() {
        int daysToAdd = 4;
        String plannedDate = DataGenerator.generateDate(daysToAdd);
        $("[data-test-id=city] input").setValue("Грозный").pressTab();
        $("[data-test-id=date] .input__control").doubleClick();
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(plannedDate).pressTab();
        $("[data-test-id=name] input").setValue("Ярослав Черников").pressTab();
        $("[data-test-id=phone] input").setValue("+12345678901").pressTab();
        $(".button__text").click();
        $("[data-test-id=agreement].input_invalid").shouldBe(visible);
        //String actual = $x("//*/[@id=\"root\"]/div/form/fieldset/div[5]/label/span[2]").getCssValue("color");
/*        String expected = "#ff5c5c!important";*/
/*        assertEquals (actual, expected);*/
    }
}
