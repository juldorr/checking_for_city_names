package com.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;

public class CityNameCheckTest {
    private static final String BASE_URL = "https://devexpress.github.io/devextreme-reactive/react/grid/docs/guides/filtering/";
    private static final Long IMPLICITLY_WAIT_SECONDS = 10L;
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        driver.manage().window().maximize();
        System.out.println("@BeforeAll executed");
    }

    @AfterAll
    public static void tear() {
        if (driver != null) {
            driver.quit();
        }
        System.out.println("@AfterAll executed");
    }

    @BeforeEach
    public void setUpThis() {
        driver.get(BASE_URL);
        System.out.println("\t@BeforeEach executed");
    }

    @AfterEach
    public void tearThis() throws InterruptedException {
        Thread.sleep(3000); // for presentation
        System.out.println("\t@AfterEach executed");
    }

    @Test
    public void testUI() {
        WebElement iframe = driver.findElement(By.xpath("(//iframe)[1]"));
        driver.switchTo().frame(iframe);
        WebElement filterInput = driver.findElement(By.xpath("(//span[text() = 'City']/../../../../..//input[@placeholder = 'Filter...'])[3]"));

        Actions action = new Actions(driver);
        action.moveToElement(filterInput).click().sendKeys("L").perform();

        String expectedNameCity1 = "Las Vegas";
        String expectedNameCity2 = "London";

        WebElement actualNameCity1 = driver.findElement(By.xpath("//td[text() = 'Las Vegas']"));
        WebElement actualNameCity2 = driver.findElement(By.xpath("//td[text() = 'London']"));

        Assertions.assertEquals(actualNameCity1.getText(), expectedNameCity1);
        Assertions.assertEquals(actualNameCity2.getText(), expectedNameCity2);
    }
}
