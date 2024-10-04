package org.example.habrtests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainPageTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.habr.com/");

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("У открытой статьи отображается соответствующий заголовок уровня h2")
    public void articleTest() {

        WebElement searchLinkAuthors = driver.findElement(By.xpath("//*[contains(text(), 'Для авторов')]"));
        searchLinkAuthors.click();

        WebElement searchLinkCase = driver.findElement(By.xpath("//a[contains(text(), 'Описываем кейс')]"));
        searchLinkCase.click();

        assertTrue(driver.findElement(By.xpath("//h2[contains(text(), 'Как описать кейс')]")).isDisplayed(), "Заголовок не найден");
    }

    @Test
    @DisplayName("Отображается заглушка, если поиск не нашёл результатов")
    void langSettingsTest() {
        WebElement searchIcon = driver.findElement(By.cssSelector("[class='tm-svg-img tm-header-user-menu__icon tm-header-user-menu__icon_search tm-header-user-menu__icon_dark']"));
        searchIcon.click();

        WebElement searchBar = driver.findElement(By.cssSelector("[class='tm-search__input tm-input-text-decorated__input']"));
        searchBar.sendKeys("651651658");
        searchBar.submit();

        assertTrue(driver.findElement(By.cssSelector("[class='tm-svg-img tm-empty-placeholder__image tm-empty-placeholder__image_variant-default']")).isDisplayed());
    }
}

