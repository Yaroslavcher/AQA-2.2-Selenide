import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDeliveryTest {

    private Faker faker;

    @BeforeEach
    void setUpAll() {
/*        faker = new Faker(new Locale("ru"));
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();*/
        Configuration.timeout = 10_000;
        open("http://localhost:9999");
    }
    @Test
    void shouldPostTheForm100Days() {
        String goodDate = LocalDate.now().plusDays(100).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Грозный").pressTab();
        $("[data-test-id=date] .input__control").doubleClick();
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(goodDate).pressTab();
        $("[data-test-id=name] input").setValue("Ярослав Черников").pressTab();
        $("[data-test-id=phone] input").setValue("+12345678901").pressTab();
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $("[data-test-id=notification] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно"));
    }

    @Test
    void shouldPostTheForm3Days() {
        String goodDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Грозный").pressTab();
        $("[data-test-id=date] .input__control").doubleClick();
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(goodDate).pressTab();
        $("[data-test-id=name] input").setValue("Ярослав Черников").pressTab();
        $("[data-test-id=phone] input").setValue("+12345678901").pressTab();
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $("[data-test-id=notification] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно"));
    }
    @Test
    void shouldNotPostTheFormBadDate() {
        String badDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Грозный").pressTab();
        $("[data-test-id=date] .input__control").doubleClick();
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(badDate).pressTab();
        $("[data-test-id=name] input").setValue("Ярослав Черников").pressTab();
        $("[data-test-id=phone] input").setValue("+12345678901").pressTab();
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $("[data-test-id=notification] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("невозможен"));
    }

    @Test
    void shouldPostTheForm() {
        $("[data-test-id=city] input").setValue("Грозный").pressTab();
        $("[data-test-id=date] .input__control").doubleClick();
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue("30.09.2022").pressTab();
        $("[data-test-id=name] input").setValue("Ярослав Черников").pressTab();
        $("[data-test-id=phone] input").setValue("+12345678901").pressTab();
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $("[data-test-id=notification] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно"));
    }

    @Test
    void shouldNotPostTheFormBadCity() {
        $("[data-test-id=city] input").setValue("Мирный").pressTab();
        $("[data-test-id=date] .input__control").doubleClick();
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue("30.09.2022").pressTab();
        $("[data-test-id=name] input").setValue("Ярослав Черников").pressTab();
        $("[data-test-id=phone] input").setValue("+12345678901").pressTab();
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input__sub").shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNotPostTheFormBadName() {
        $("[data-test-id=city] input").setValue("Грозный").pressTab();
        $("[data-test-id=date] .input__control").doubleClick();
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue("30.09.2022").pressTab();
        $("[data-test-id=name] input").setValue("Ivan Ivanov").pressTab();
        $("[data-test-id=phone] input").setValue("+12345678901").pressTab();
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input__sub").shouldHave(text("русски"));
    }

    @Test
    void shouldNotPostTheFormBadPhone() {
        $("[data-test-id=city] input").setValue("Грозный").pressTab();
        $("[data-test-id=date] .input__control").doubleClick();
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue("30.09.2022").pressTab();
        $("[data-test-id=name] input").setValue("Ярослав Черников").pressTab();
        $("[data-test-id=phone] input").setValue("+1234567890").pressTab();
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input__sub").shouldHave(text("Должно быть 11 цифр"));
    }

    @Test
    void shouldNotPostTheFormNoAgreement() {
        $("[data-test-id=city] input").setValue("Грозный").pressTab();
        $("[data-test-id=date] .input__control").doubleClick();
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue("30.09.2022").pressTab();
        $("[data-test-id=name] input").setValue("Ярослав Черников").pressTab();
        $("[data-test-id=phone] input").setValue("+12345678901").pressTab();
/*        $("[data-test-id=agreement]").click();*/
        $(".button__text").click();
        String actual = $x("//*/[@id=\"root\"]/div/form/fieldset/div[5]/label/span[2]").getCssValue("color");
        String expected = "#ff5c5c!important";
        assertEquals (actual, expected);
    }
}
