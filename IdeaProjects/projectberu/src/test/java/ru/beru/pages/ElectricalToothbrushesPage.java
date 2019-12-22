package ru.beru.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ElectricalToothbrushesPage {
    WebDriver driver;
    WebDriverWait wait;

    public ElectricalToothbrushesPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    @FindBy(xpath = "//div[@data-auto=\"filter-range-glprice\"]//span[@data-auto=\"filter-range-min\"]//input")
    private WebElement fieldPriceMin;

    @FindBy(xpath = "//div[@data-auto=\"filter-range-glprice\"]//span[@data-auto=\"filter-range-max\"]//input")
    private WebElement fieldPriceMax;

    @FindBy(css = "[class=\"_3GNV1gy3cc\"]")
    private WebElement labelPriceRange;

    @FindBy(xpath = "//span[contains(@class, '_3ioN70chUh _3XRVQbB83A')]")
    private WebElement popupFounded;

    //todo ask about correction
    @FindBy(xpath = "//div[@class=\"_1uhsh_io8o\"]//div[@class=\"_3rWYRsam78\"][last()]/div[last()]//div[@class=\"_1RjY7YIluf _1zYszmgEzn\"][last()-1]//span[@class=\"_2w0qPDYwej\"]")
    private WebElement penultimateToothbrush;

    @FindBy(css = "[class=\"_1LEwf9X1Gy\"]")
    private WebElement buttonGotoCart;

    private By locatorFoundedGoods = By.className("_3rWu3-6RDl qpgDgmh6Hn _11QbuC0gtX _1zxBwSfbGK _1mXFu6EZpv >wrItvb7JRv");
    private By locatorCartPage = By.className("wn9mZbgWbv");


    @Step("Open electrical toothbrushes page")
    public void open() {
        driver.get("https://beru.ru/catalog/elektricheskie-zubnye-shchetki-v-saratove/80961/list?hid=278374&track=fr_ctlg");
        wait.until(ExpectedConditions.visibilityOf(fieldPriceMin));
    }

    @Step("Set start price")
    public void setMinPrice(int price) {
        fieldPriceMin.sendKeys(Integer.toString(price));
    }

    @Step("Set minimal price")
    public void setMaxPrice(int price) {
        fieldPriceMax.sendKeys(Integer.toString(price));
    }

    @Step("Check the price range is correct")
    public void checkPriceRangeCorrect(int low, int max) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(labelPriceRange));
        String priceRange = low + " — " + max + " ₽";
        Assert.assertEquals(priceRange, labelPriceRange.getText());

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return popupFounded.isDisplayed();
            }
        });
    }

    @Step("Purchasing penultimate toothbrush")
    //fixme taking too many screenshots
    public void purchaseLast() {
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(penultimateToothbrush));
        penultimateToothbrush.click();
        wait.until(ExpectedConditions.elementToBeClickable(penultimateToothbrush));
    }

    @Step("Go to cart")
    public void gotoCart() {
        buttonGotoCart.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locatorCartPage));
    }


}
