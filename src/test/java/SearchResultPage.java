import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;
public class SearchResultPage {
    SelenideElement FlightsTable, RegistrationButton, NoFlightsMessage;
    public SearchResultPage() {
        FlightsTable = $x("//table[@id='flightsTable']");
        RegistrationButton = FlightsTable.find(By.xpath(".//button[text()='Зарегистрироваться']"));
        NoFlightsMessage = FlightsTable.find(By.xpath(".//td[@colspan='7']"));
    }
    public void verify_flights_exists() {
        RegistrationButton.shouldBe(exist);
    }
    public void verify_no_flights() {
        NoFlightsMessage.shouldHave(text("Рейсов по вашему запросу не найдено."));
    }
    public void register() {
        RegistrationButton.click();
    }
}