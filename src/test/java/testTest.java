import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class testTest {
    static WebDriver driver;

    String URL = "https://test-m.sd.nau.run/sd/";
    String LOGIN = "d.vereshchagin";
    String PASS = "123";
    String USERNAME_FIELD = "//input[@id='username']";
    String PASSWORD_FIELD = "//input[@id='password']";
    String AUTH_BUTTON_SUBMIT = "//input[@id='submit-button']";
    String EXIT_BUTTON = "//a[@id='gwt-debug-logout']";
    String EXIT_BUTTON_TEXT = "Выйти";
    String ADD_TO_FAVORITE_BUTTON = "//span[@id='favorite']";
    String FAVORITE_APPLY_BUTTON = "//div[@id='gwt-debug-apply']";
    String FAVORITE_MENU = "//div[@id='gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9']";
    String CARD_IN_FAVORITES = "//a[@id='gwt-debug-title']/div";
    String FAVORITES_OPTIONS = "//span[@id='gwt-debug-editFavorites']";
    String DELETE_ICON_IN_OPTIONS = "//table[@id='gwt-debug-favoritesEditTable']/tbody/tr/td[6]/div/span";
    String DELETE_SUBMIT = "//div[@id='gwt-debug-YES']";
    String FAVORITES_SAVE = "//div[@id='gwt-debug-apply']";

    String FAVORITES_LIST = "//div[@id='gwt-debug-menuItem.uuid:employee$32023']/table";
    //String FAVORITES_LIST = "//table[@id='gwt-debug-favoritesEditTable']/tbody/tr/td[3]";
    @Test
    public void addObject() throws InterruptedException {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().setSize(new Dimension(999, 999));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        driver.get(URL);

        sendKeys(USERNAME_FIELD, LOGIN);
        sendKeys(PASSWORD_FIELD, PASS);
        click(AUTH_BUTTON_SUBMIT);

        checkIfText(EXIT_BUTTON, EXIT_BUTTON_TEXT);

        click(ADD_TO_FAVORITE_BUTTON);
        click(FAVORITE_APPLY_BUTTON);
        click(FAVORITE_MENU);

        checkIfExist(CARD_IN_FAVORITES);

        click(FAVORITES_OPTIONS);
        click(DELETE_ICON_IN_OPTIONS);

        click(DELETE_SUBMIT);
        click(FAVORITES_SAVE);

        click(FAVORITE_MENU);
        click(EXIT_BUTTON);
    }

    @Test
    public void deleteObject() throws InterruptedException {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().setSize(new Dimension(999, 999));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get(URL);

        sendKeys(USERNAME_FIELD, LOGIN);
        sendKeys(PASSWORD_FIELD, PASS);
        click(AUTH_BUTTON_SUBMIT);

        checkIfText(EXIT_BUTTON, EXIT_BUTTON_TEXT);

        click(ADD_TO_FAVORITE_BUTTON);
        click(FAVORITE_APPLY_BUTTON);
        click(FAVORITE_MENU);

        click(FAVORITES_OPTIONS);
        click(DELETE_ICON_IN_OPTIONS);

        click(DELETE_SUBMIT);
        click(FAVORITES_SAVE);

        checkIfTableEmpty(FAVORITES_LIST);

        click(FAVORITE_MENU);
        click(EXIT_BUTTON);
    }

    public WebElement waitElement(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    public void click(String xpath) {
        waitElement(xpath).click();
    }

    public void checkIfText(String xpath, String assertText) {
        String textInside = waitElement(xpath).getText();

        String msg = String.format("Назавние объекта не было корректно. Ожидалось: %s, Результат: %s", assertText, textInside);
        Assertions.assertEquals(waitElement(xpath).getText(), assertText, msg);
    }

    public void checkIfExist(String xpath) {
        waitElement(xpath).isDisplayed();
    }

    public void checkIfTableEmpty(String xpath) {
        List<WebElement> elements = driver.findElements(By.xpath(xpath));
        Assertions.assertTrue(elements.isEmpty(), "Объект не удалился");
    }

    public void sendKeys(String xpath, String keysToSend) {
        waitElement(xpath).sendKeys(keysToSend);
    }

    @AfterAll
    public static void close() {
        driver.close();
    }
}
