import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import swoop.data.Constants;

public class LandingPageTests extends Constants {
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
    public void activeCategoryTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement categoriesMenu = driver.findElement(By.className(CATEGORIES_CN));
        Actions hoverOnCategories = new Actions(driver);
        hoverOnCategories.click(categoriesMenu).perform();
        WebElement sportSubmenu = driver.findElement(By.cssSelector(SPORT_CSS));
        Actions moveOnSport = new Actions(driver);
        moveOnSport.moveToElement(sportSubmenu).perform();
        Thread.sleep(3000);
        WebElement elementToClick = driver.findElement(By.xpath(CARTING_XPASS));
        Actions actions = new Actions(driver);
        actions.click(elementToClick).perform();
        Thread.sleep(3000);
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, EXCEPTED_CARTING_URL);

        WebElement kartingiElement = driver.findElement(By.cssSelector(COLORED_CARTING_CSS));
        String colorValue = kartingiElement.getCssValue("color");
        System.out.println(colorValue);
        String expectedColor = "rgba(110, 124, 250, 1)";
        Assert.assertEquals(colorValue,expectedColor);

    }
    @Test
    public void logoTest(){
        String landingPage = SWOOP_URL;
        driver.findElement(By.linkText(DASVENEBA_LT)).click();
        driver.findElement(By.className(SWOOP_LOGO_CN)).click();
        Assert.assertEquals(driver.getCurrentUrl(),landingPage);
    }
}
