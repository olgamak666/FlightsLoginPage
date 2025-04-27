import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class POMFlightsTests {
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
    @DisplayName("POM-01. Успешный логин + успешный поиск + успешная регистрация")
    void test01() {
        FlightsLoginPage login_page = new FlightsLoginPage();
        login_page.login("standard_user", "stand_pass1");
        login_page.verify_successful_login();

        SearchPage search_page = new SearchPage();
        search_page.search("Москва", "Лондон", "28-04-2025");

        SearchResultPage search_result_page = new SearchResultPage();
        search_result_page.verify_flights_exists();
        search_result_page.register();

        RegistrationPage registration_page = new RegistrationPage();
        registration_page.finish_registration_success();
    }

    @Test
    @DisplayName("POM-02. Неуспешный логин")
    void test02() {
        FlightsLoginPage login_page = new FlightsLoginPage();
        login_page.login("standard_user", "wrong_stand_pass1");
        login_page.verify_wrong_username_or_password();
    }
    @Test
    @DisplayName("POM-03. Не задана дата вылета")
    void test03() {
        FlightsLoginPage login_page = new FlightsLoginPage();
        login_page.login("standard_user", "stand_pass1");
        login_page.verify_successful_login();

        SearchPage search_page = new SearchPage();
        search_page.search("Москва", "Лондон", "");
        search_page.verify_wrong_date();
    }
    @Test
    @DisplayName("POM-04. Не найдены рейсы")
    void test04() {
        FlightsLoginPage login_page = new FlightsLoginPage();
        login_page.login("standard_user", "stand_pass1");
        login_page.verify_successful_login();

        SearchPage search_page = new SearchPage();
        search_page.search("Казань", "Париж", "23-04-2025");

        SearchResultPage search_result_page = new SearchResultPage();
        search_result_page.verify_no_flights();
    }
    @Test
    @DisplayName("POM-05. Регистрация - некорректно заполнен номер паспорта")
    void test05() {
        FlightsLoginPage login_page = new FlightsLoginPage();
        login_page.login("standard_user", "stand_pass1");
        login_page.verify_successful_login();

        SearchPage search_page = new SearchPage();
        search_page.search("Москва", "Лондон", "28-04-2025");

        SearchResultPage search_result_page = new SearchResultPage();
        search_result_page.verify_flights_exists();
        search_result_page.register();

        RegistrationPage registration_page = new RegistrationPage();
        registration_page.fill_form(null, "Номер паспорта", null, null);
        registration_page.finish_registration_wrong_passport_number();
    }

    @Test
    @DisplayName("POM-86. Сложный сценарий - прохождение всех щагов со второго раза");
    void test06 () {

    }
}