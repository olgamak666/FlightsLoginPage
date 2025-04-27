import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
public class FlightsLoginPage {
    SelenideElement Auth, Username, Password, LoginButton, Message;
    public FlightsLoginPage() {
        Auth = $("div[id=loginContainer]");
        Username = $("#username");
        Password = $("#password");
        LoginButton = $("#loginButton");
        Message = $("#message");
    }
    public void login(String username, String password) {
        Username.setValue(username);
        Password.setValue(password);
        LoginButton.click();
    }
    public void verify_successful_login() {
        Message.shouldHave(text("Вход в систему выполнен успешно! Загрузка..."));
    }
    public void verify_wrong_username_or_password() {
        Message.shouldHave(text("Неверное имя пользователя или пароль."));
    }

}