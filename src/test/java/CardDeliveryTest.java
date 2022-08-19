import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    Faker faker = new Faker();

    String firstName = faker.name().firstName(); // Emory
    String lastName = faker.name().lastName(); // Barton

    @Test
    void shouldPostTheForm() {
        Configuration.timeout = 10_000;
        Configuration.holdBrowserOpen = true;
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
        $("[data-test-id=agreement] input").click();
/*        class = "button__text"*/.click();
        $x(//*[contains(text(), 'успешно')]).setValue(23456789879999);   //xpath поиск по всему документу


                Configuration.timeout = 15_000; //проверка условия - состояние загрузки не более 15 секунд
        $("[data-test-id=notification] class = "notification__title").shouldHave(cssClass("Успешно"));
                // $(withText("успешно")).shouldBe(visible);

    }

}
