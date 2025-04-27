import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
public class SearchPage {
    SelenideElement WelcomeMessage, DepartureCity, ArrivalCity, DepartureDate, SearchButton, Message;
    public SearchPage() {
        WelcomeMessage = $("div[id=loginContainer]");
        DepartureCity = $("#departureCity");
        ArrivalCity = $("#arrivalCity");
        DepartureDate = $("#departureDate");
        SearchButton = $x("//button[text()='Найти']");
        Message = $("#searchMessage");
    }
    public void search(String departure_city, String arrival_city, String departure_date) {
        DepartureCity.selectOption(departure_city);
        ArrivalCity.selectOption(arrival_city);
        DepartureDate.setValue(departure_date);
        SearchButton.click();
    }
    public void verify_wrong_date() {
        Message.shouldHave(text("Пожалуйста, укажите дату вылета."));
    }
}