package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTestV2 {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);

        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSendFormWithoutCheckbox() {
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ветров Матвей");
        formElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+71468527942");
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement actual = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid"));
        assertTrue(actual.isDisplayed());
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", actual.getText().trim());
    }

    @Test
    void shouldSendFormWithEmptyName() {
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        formElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+72548965231");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(actual.isDisplayed());
        assertEquals("Поле обязательно для заполнения", actual.getText().trim());
    }

    @Test
    void shouldSendFormWithLatinInName() {
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Nikolaev Pavel");
        formElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+71468527942");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(actual.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.getText().trim());
    }

    @Test
    void shouldSendFormWithNumbersInName() {
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("854123");
        formElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+87416523984");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(actual.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.getText().trim());
    }

    @Test
    void shouldSendFormWithSpecialSymbolsInName() {
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("*$^&@");
        formElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+98741256325");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(actual.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.getText().trim());
    }

    @Test
    void shouldSendFormWithEmptyPhone() {
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Маркова Ирина");
        formElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(actual.isDisplayed());
        assertEquals("Поле обязательно для заполнения", actual.getText().trim());
    }
    @Test
    void shouldSendFormWithPhoneWithoutPlus() {
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Смирнова Мария");
        formElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("79854125698");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(actual.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.getText().trim());
    }

    @Test
    void shouldSendFormWithPhone10() {
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Антонова Светлана");
        formElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+8954213698");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(actual.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.getText().trim());
    }

    @Test
    void shouldSendFormWithPhone12() {
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Силин Сергей");
        formElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+845123697458");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(actual.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.getText().trim());
    }

    @Test
    void shouldSendFormWithPhone3() {
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Миронов Олег");
        formElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+785");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(actual.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.getText().trim());
    }

    @Test
    void shouldSendFormWithPhone20() {
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Павлова Ольга");
        formElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+76521448523698559687");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(actual.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.getText().trim());
    }

    @Test
    void shouldSendFormWithLatinInPhone() {
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Мишин Денис");
        formElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("ertyukjhg");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(actual.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.getText().trim());
    }

    @Test
    void shouldSendFormWithCirillicInPhone() {
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Серов Василий");
        formElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("вапнгро");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(actual.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.getText().trim());
    }

    @Test
    void shouldSendFormWithSpecialSymbolsPhone() {
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Новикова Анна");
        formElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("№*?*");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(actual.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.getText().trim());
    }
}
