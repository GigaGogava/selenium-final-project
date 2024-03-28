import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import swoop.data.Constants;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static org.testng.Assert.assertEquals;

public class HolidayPageTests extends Constants {
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
    public void descendingOrderTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement travelMenu = driver.findElement(By.linkText(DASVENEBA_LT));
        travelMenu.click();
        WebElement sort = driver.findElement(By.id(SORT_ID));
        Select select = new Select(sort);
        select.selectByVisibleText("ფასით კლებადი");
        Thread.sleep(3000);
        /*wait.until(ExpectedConditions.invisibilityOfAllElements
                (driver.findElements(By.className(ITEMS_GRIDLIST_CN))));*/
        // Find the most expensive offer among all offers
        List<WebElement> offerElements = driver.findElements(By.className(ITEMS_GRIDLIST_CN));
        WebElement highestPriceElement = null;
        int highestPrice = 0;
        for (WebElement offer : offerElements) {
            int currentPrice = parseInt(offer.findElement
                    (By.className(PRICE_ELEMENT_CN)).getText().replaceAll("[^0-9]", ""));
            if (currentPrice > highestPrice) {
                highestPriceElement = offer;
                highestPrice = currentPrice;
            }
        }
        List<WebElement> sortedOffers = driver.findElements(By.className(ITEMS_GRIDLIST_CN));
        WebElement firstOfferInList = sortedOffers.get(0);
        int sortedPrice = parseInt(firstOfferInList.findElement
                (By.className(PRICE_ELEMENT_CN)).getText().replaceAll("[^0-9]", ""));
        System.out.println(sortedPrice);
        assertEquals(highestPrice, sortedPrice, "The most expensive offer is not displayed first in the list.");

    }

    @Test
    public void ascendingOrderTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement travelMenu = driver.findElement(By.linkText(DASVENEBA_LT));
        travelMenu.click();
        WebElement sort = driver.findElement(By.id(SORT_ID));
        Select select = new Select(sort);
        select.selectByVisibleText("ფასით ზრდადი");
        Thread.sleep(2000);
        List<WebElement> offerElements = driver.findElements(By.className(ITEMS_GRIDLIST_CN));
        WebElement lowestPriceElement = null;
        int lowestPrice = Integer.MAX_VALUE;
        for (WebElement offer : offerElements) {
            int currentPrice = parseInt(offer.findElement
                    (By.className(PRICE_ELEMENT_CN)).getText().replaceAll("[^0-9]", ""));
            if (currentPrice < lowestPrice) {
                lowestPriceElement = offer;
                lowestPrice = currentPrice;
            }
        }
        List<WebElement> sortedOffers = driver.findElements(By.className(ITEMS_GRIDLIST_CN));
        WebElement firstOfferInList = sortedOffers.get(0);
        int firstOffer = parseInt(firstOfferInList.findElement
                (By.className(PRICE_ELEMENT_CN)).getText().replaceAll("[^0-9]", ""));
        Assert.assertEquals(lowestPrice, firstOffer, "The cheapest offer is not displayed first in the list.");
    }

    @Test
    public void filterTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement travelMenu = driver.findElement(By.linkText(DASVENEBA_LT));
        travelMenu.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(DEAL_CONTAINER_CN)));
        driver.findElement(By.xpath(KOTEJI_CB_XPASS)).click();
        /*wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.className(DEAL_CONTAINER_CN))));*/
        Thread.sleep(3000);
        List<WebElement> offerElements = driver.findElements(By.xpath(ITEMS_GRIDLIST_XPASS));
        List<String> offerTexts = new ArrayList<>();
        for (WebElement offer : offerElements) {
            offerTexts.add(offer.getText().toLowerCase());
        }
        for (WebElement offer : offerElements) {
            try {
                String offerText = offer.getText().toLowerCase();
                if (!offerText.contains("კოტეჯი".toLowerCase())) {
                    System.out.println("WARNING: Offer does not contain 'კოტეჯი': " + offerText);
                }
            } catch (Exception e) {
                System.out.println("Error getting offer text: " + e.getMessage());
            }
        }
            // Get the sort dropdown element
        WebElement sort = driver.findElement(By.id(SORT_ID));
        Select select = new Select(sort);
        select.selectByVisibleText("ფასით ზრდადი");
        Thread.sleep(3000);
        /*wait.until(ExpectedConditions.invisibilityOfAllElements
                (driver.findElements(By.className(ITEMS_GRIDLIST_CN))));*/
        List<WebElement> offerElements1 = driver.findElements(By.className(ITEMS_GRIDLIST_CN));
        WebElement lowestPriceElement = null;
        int lowestPrice = Integer.MAX_VALUE;
        for (WebElement offer : offerElements1) {
            int currentPrice = parseInt(offer.findElement
                    (By.className(PRICE_ELEMENT_CN)).getText().replaceAll("[^0-9]", ""));
            if (currentPrice < lowestPrice) {
                lowestPriceElement = offer;
                lowestPrice = currentPrice;
            }
        }
        List<WebElement> sortedOffers = driver.findElements(By.className(ITEMS_GRIDLIST_CN));
        WebElement firstOfferInList = sortedOffers.get(0);
        int firstOffer = parseInt(firstOfferInList.findElement
                (By.className(PRICE_ELEMENT_CN)).getText().replaceAll("[^0-9]", ""));
        Assert.assertEquals(lowestPrice, firstOffer, "The cheapest offer is not displayed first in the list.");
    }
    @Test
    public void priceRangeTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement travelMenu = driver.findElement(By.linkText(DASVENEBA_LT));
        travelMenu.click();
        WebElement scrolled = driver.findElement(By.cssSelector(FOR_SCROLL_TO_PRICE_CSS));
        js.executeScript("arguments[0].scrollIntoView();", scrolled);
        WebElement priceFromInput = driver.findElement(By.cssSelector(PRICE_FROM_INPUT_CSS));
        priceFromInput.sendKeys(("120"));
        WebElement priceToInput = driver.findElement(By.cssSelector(PRICE_TO_INPUT_CSS));
        priceToInput.sendKeys("459");
        driver.findElement(By.cssSelector(SEARCH_BTN)).click();
        Thread.sleep(3000);
/*
        wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.className(DEAL_CONTAINER_CN))));
*/
        int minPrice = 120;
        int maxPrice = 459;
        List<WebElement> offerElements = driver.findElements(By.className(ITEMS_GRIDLIST_CN));
        for (WebElement offer : offerElements) {
            WebElement offerPriceElement = offer.findElement(By.className(PRICE_ELEMENT_CN));
            int offerPrice = Integer.parseInt
                    (offerPriceElement.getText().replaceAll("[^0-9]", ""));
            Assert.assertTrue(offerPrice > minPrice && offerPrice < maxPrice,
                    "Offer price is not within the specified range");
        }
    }
}