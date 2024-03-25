import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import swoop.data.Constants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MoviePageTests extends Constants {
    private WebDriver driver;

    @BeforeTest
    @Parameters("browser")
    public void setup(String browser) {
        System.out.println(browser);
        if (browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            driver.manage().window().maximize();

        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
        }

        driver.get(SWOOP_URL);
        driver.findElement(By.className(C00KIES_CN)).click();

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void moviePageTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        driver.findElement(By.linkText(KINO_LT)).click();
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.className(FIRST_MOVIE))).perform();
        driver.findElement(By.cssSelector(BUY_TKT_CSS)).click();
        JavascriptExecutor jsExecute = (JavascriptExecutor) driver;
        jsExecute.executeScript("window.scrollBy(0, 600);");
        driver.findElement(By.xpath(EAST_POINT_XPATH)).click();
        //cant access element to make list.
        driver.findElement(By.xpath(DATE_PICK)).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath(CHOOSE_LAST)).click();
        String exceptedName =  "კუნგ ფუ პანდა 4 PG",
            exceptedPlace = "კავეა ისთ ფოინთი",
            exceptedDate = "26 მარტი 21:30";
        // Find the movie name
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TICKET_POPUP)));
        String movieName = driver.findElement(By.xpath(SEANSE_DETAILS)).getText();
        Assert.assertTrue(movieName.contains(exceptedName));
        Assert.assertTrue(movieName.contains(exceptedDate));
        Assert.assertTrue(movieName.contains(exceptedPlace));
        driver.findElement(By.xpath(SEAT_FREE)).click();
        driver.findElement(By.xpath(REGISTER)).click();
        driver.findElement(By.xpath(EMAIL_XPATH)).sendKeys("ariqa");
        driver.findElement(By.xpath(PASSWORD_XPATH)).sendKeys("121212");
        driver.findElement(By.xpath(RETYPE_PASS_XPATH)).sendKeys("121212");
        driver.findElement(By.cssSelector(GENDER_RADIO_CSS)).click();
        driver.findElement(By.id(NAME_ID)).sendKeys("giga");
        driver.findElement(By.id(SURNAME_ID)).sendKeys("gogava");
        jsExecute.executeScript("window.scrollBy(0, 1000);");
        /*WebElement birthDate = driver.findElement(By.className("select2-selection__arrow"));
        wait.until(ExpectedConditions.elementToBeClickable(birthDate)).click();
        birthDate.click();*/
        driver.findElement(By.id(PHONE_ID)).sendKeys("599955999");
        jsExecute.executeScript("window.scrollBy(0, 3000);");
        WebElement checkBoxElement = driver.findElement(By.cssSelector(RULES_CHECK_BOX));
        Thread.sleep(3000);
        checkBoxElement.click();
        WebElement checkBoxElement1 = driver.findElement(By.xpath(RULES_CB1));
        Thread.sleep(3000);
        checkBoxElement1.click();
        driver.findElement(By.id(REGISTER_BTN)).click();
        String exceptedErrorMessage = "მეილის ფორმატი არასწორია!";
        String mailErrorMessage = driver.findElement(By.id(EMAIL_ERROR)).getText();
        Assert.assertEquals(exceptedErrorMessage,mailErrorMessage);
        }

}
