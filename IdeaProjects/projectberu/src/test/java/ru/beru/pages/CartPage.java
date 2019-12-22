package ru.beru.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.beru.WebDriverSettings;

public class CartPage extends WebDriverSettings {

    WebDriver driver;
    WebDriverWait wait;

    public CartPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    @FindBy(css = "[class=\"_2YHTmhZmt4\"]")
    private WebElement labelLeftForFreeShipment;

    @FindBy(css="[class=\"_1oBlNqVHPq\"]")
    private WebElement labelTotal;

    @FindBy(css="[class=\"_4qhIn2-ESi Pjv3h3YbYr THqSbzx07u _39B7yXQbvm _2W4X8tX6r0\"]")
    private WebElement buttonCheckout;

    @FindBy(xpath = "//div[@class=\"_3MqS53YE3Q\"]//div[@class=\"_1u3j_pk1db\"]")
    private WebElement labelToothbrushPrice;

    //fixme xpath
    @FindBy(xpath = "//div[@data-auto=\"CartOffer\"]//input[@value]")
    private WebElement fieldCount;

    @FindBy(css = "[class=\"_2TbI0lRCD8\"]")
    private WebElement buttonDelete;

    @FindBy(css="[class=\"_2TFWzc3clT\"]")
    private WebElement labelClear;

    By locatorCheckoutPage = By.className("_1e2FY_93Ro");
    By locatorButtonDelete = By.className("uL4H6qKhvR");
    By locatorDeletionCross = By.className("_45y2-1v_xT");

    @Step("Opening cart page")
    public void open() {
        driver.get("https://beru.ru/my/cart");
    }

    @Step("Checking is shipping free")
    public void checkFreeShipment() {
        wait.until(ExpectedConditions.visibilityOf(labelLeftForFreeShipment));
        String[] priceStr = labelLeftForFreeShipment.getText().split(" ");
        int leftForFree = Integer.parseInt(priceStr[0]);
        if (leftForFree < 10) {
            leftForFree *= 100;
            leftForFree += Integer.parseInt(priceStr[1]);
        }

        System.out.println(leftForFree);
    }

    @Step("Opening checkout")
    public void openCheckout() {
        buttonCheckout.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locatorCheckoutPage));
    }

    @Step("Increasing total price to {price}")
    public void increaseTotalTo(double price) {
        String[] nums = labelToothbrushPrice.getText().split("\\D+");
        int toothbrushPrice = Integer.parseInt(nums[0]);
        if (nums.length > 1) {
            toothbrushPrice *= 1000;
            toothbrushPrice += Integer.parseInt(nums[1]);
        }

        int k = (int) Math.ceil(price / toothbrushPrice);
        fieldCount.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        fieldCount.sendKeys(Integer.toString(k));
        wait.until(ExpectedConditions.visibilityOf(labelTotal));
        wait.until(ExpectedConditions.elementToBeClickable(buttonCheckout));
    }

    @Step("Cleaning cart")
    public void cleanCart() {
        open();
        if (driver.findElements(locatorButtonDelete).size() != 0) {
            buttonDelete.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(locatorDeletionCross));
            open();
        }
        wait.until(ExpectedConditions.visibilityOf(labelClear));
    }



}
