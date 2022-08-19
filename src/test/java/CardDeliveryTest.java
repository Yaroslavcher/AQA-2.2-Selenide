import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    Faker faker = new Faker();

    String firstName = faker.name().firstName(); // Emory
    String lastName = faker.name().lastName(); // Barton

    @Test
    void shouldNotPostTheFormTooLate() {
        String toolate = LocalDate.now().plusDays(100).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Грозный").pressTab();
        $("[data-test-id=date] .input__control").doubleClick();
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(toolate).pressTab();
        $("[data-test-id=name] input").setValue("Ярослав Черников").pressTab();
        $("[data-test-id=phone] input").setValue("+12345678901").pressTab();
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $("[data-test-id=notification] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно"));
    }

    @Test
    void shouldNotPostTheFormTooEarly() {
        String tooearly = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Грозный").pressTab();
        $("[data-test-id=date] .input__control").doubleClick();
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(tooearly).pressTab();
        $("[data-test-id=name] input").setValue("Ярослав Черников").pressTab();
        $("[data-test-id=phone] input").setValue("+12345678901").pressTab();
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $("[data-test-id=notification] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("невозможен"));
    }

    @Test
    void shouldPostTheForm() {
        Configuration.timeout = 10_000;
        Configuration.browserSize = "1200x600";
        Configuration.headless = true;
        open("http://localhost:9999");
/*        SelenideElement form = $();*/
        $("[data-test-id=city] input").setValue("Грозный").pressTab();
        //логика с LocalDate()
        $("[data-test-id=date] .input__control").doubleClick();
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue("17.08.2022").pressTab();
        $("[data-test-id=name] input").setValue("Ярослав Черников").pressTab();
        $("[data-test-id=phone] input").setValue("+12345678901").pressTab();
        $("[data-test-id=agreement]").click();
/*        class = "button__text"*/.click();
        $x(//*[contains(text(), 'успешно')]).setValue(23456789879999);   //xpath поиск по всему документу


                Configuration.timeout = 15_000; //проверка условия - состояние загрузки не более 15 секунд
        $("[data-test-id=notification] class = "notification__title").shouldHave(cssClass("Успешно"));
                // $(withText("успешно")).shouldBe(visible);

    }

}
