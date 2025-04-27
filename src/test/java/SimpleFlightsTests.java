import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class SimpleFlightsTests {
    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @BeforeEach
    void setUp() {
        open("https://slqa.ru/cases/DeepSeekFlights/");
        getWebDriver().manage().window().maximize();
    }
    //Тест-кейсы
    @Test
    @DisplayName("01. Успешный логин + успешный поиск + успешная регистрация")
    void test01() {
        $("#username").setValue("standard_user");
        $("#password").setValue("stand_pass1");
        $("#loginButton").click();

        $("#departureCity").selectOption("Москва");
        $("#arrivalCity").selectOption("Лондон");
        $("#departureDate").setValue("28-04-2025");
        $x("//button[text()='Найти']").click();

        $x("//table[@id='flightsTable']//button[text()='Зарегистрироваться']").click();

        $x("//button[text()='Завершить регистрацию']").click();

        Alert alert = switchTo().alert();
        System.out.println(alert.getText());
        assertTrue(alert.getText().contains("Бронирование завершено"));
        alert.accept();
    }
    @Test
    @DisplayName("02. Неуспешный логин")
    void test02() {
        $("#username").setValue("standard_user");
        $("#password").setValue("wrong_stand_pass1");
        $("#loginButton").click();

        $("#message").shouldHave(text("Неверное имя пользователя или пароль."));
    }
    @Test
    @DisplayName("03. Не задана дата вылета")
    void test03() {
        $("#username").setValue("standard_user");
        $("#password").setValue("stand_pass1");
        $("#loginButton").click();

        $("#departureCity").selectOption("Москва");
        $("#arrivalCity").selectOption("Лондон");
        $("#departureDate").setValue("");

        $x("//button[text()='Найти']").click();
        $("#searchMessage").shouldHave(text("Пожалуйста, укажите дату вылета."));
    }
    @Test
    @DisplayName("04. Не найдены рейсы")
    void test04() {
        $("#username").setValue("standard_user");
        $("#password").setValue("stand_pass1");
        $("#loginButton").click();
        $("#departureCity").selectOption("Казань");
        $("#arrivalCity").selectOption("Париж");
        $("#departureDate").setValue("23-04-2025");
        $x("//button[text()='Найти']").click();
        //$("td[colspan='7']").shouldHave(text("Рейсов по вашему запросу не найдено."));
        $x("//table[@id='flightsTable']").find(By.xpath(".//td[@colspan='7']")).shouldHave(text("Рейсов по вашему запросу не найдено."));
    }
    @Test
    @DisplayName("05. Регистрация - некорректно заполнен номер паспорта")
    void test05() {
        $("#username").setValue("standard_user");
        $("#password").setValue("stand_pass1");
        $("#loginButton").click();

        $("#departureCity").selectOption("Москва");
        $("#arrivalCity").selectOption("Лондон");
        $("#departureDate").setValue("28-04-2025");
        $x("//button[text()='Найти']").click();

        $x("//table[@id='flightsTable']//button[text()='Зарегистрироваться']").click();

        $("#passportNumber").setValue("Номер паспорта");
        $x("//button[text()='Завершить регистрацию']").click();

        $("#registrationMessage").shouldHave(text("Номер паспорта должен содержать только цифры и пробелы."));
    }
}