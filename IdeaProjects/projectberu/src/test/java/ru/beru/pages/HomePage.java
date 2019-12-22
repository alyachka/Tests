package ru.beru.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    @FindBy(css = "[class=\"_1r1GkezLi0\"]")
    private WebElement buttonBeru;

    @FindBy(css = "[class=\"_3odNv2Dw2n\"]")
    private WebElement buttonAuth;

    @FindBy(xpath = "//span[contains(@data-auto,'region-form-opener')]//span[2]")
    private WebElement buttonCity;

    @FindBy(css = "[class=\"_1U2ErCeoqP\"]")
    private WebElement popupCity;

    @FindBy(xpath = "//div[contains(@data-auto,'region-popup')] //input[contains(@class,'_2JDvXzYsUI')]")
    private WebElement fieldCity;

    @FindBy(id = "react-autowhatever-region")
    private WebElement listboxCities;

    @FindBy(css = "[class=\"_4qhIn2-ESi Pjv3h3YbYr THqSbzx07u\"]")
    private WebElement buttonOk;

    @FindBy(css = "[class=\"_1FEpprw_Km\"]")
    private WebElement buttonMyProfile;

    @FindBy(css = "[href=\"/my/settings?track=menu\"]")
    private WebElement buttonSettings;

    @FindBy(css = "[class=\"_3RM4_n5whA\"]")
    private WebElement buttonCatalog;

    @FindBy(css = "[class=\"_3JUsAgML4w\"]")
    private WebElement catalog;

    @FindBy(css = "a[title=\"Электрические зубные щетки\"]")
    private WebElement buttonElectricalToothbrushes;

    @FindBy(css = "a[title=\"Красота и гигиена\"]")
    private WebElement buttonBeautyAndHygiene;

    private By locatorButtonCity = By.xpath("//span[contains(@data-auto,'region-form-opener')]//span[2]");
    private By locatorFirstCityOfList = By.className("_229JDbp_Z8");
    private By locatorAuthPage = By.id("passp-field-login");
    private By locatorSettingsPage = By.className("_38iDpDiSsi");
    private By locatorBeautyAndHygienePage = By.cssSelector("a[title=\"Красота и гигиена\"]");
    private By locatorElectricalToothbrushesPage = By.className("ZsTILNLaud");


    @Step("Open homepage")
    public void open() {
        driver.get("https://beru.ru/");
        wait.until(ExpectedConditions.visibilityOf(buttonAuth));
    }

    @Step("Open sign-in page")
    public void openPassportPage() {
        buttonAuth.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locatorAuthPage));
    }

    @Step("Check that current city is {cityName}")
    public void checkCity(String cityName) {
        wait.until(ExpectedConditions.textToBe(locatorButtonCity, cityName));
        Assert.assertEquals(cityName, buttonCity.getText());
    }

    @Step("Change current city to {cityName}")
    public void changeCity(String cityName) {
        buttonCity.click();
        wait.until(ExpectedConditions.visibilityOf(popupCity));
        fieldCity.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);

        for (int i = 0; i < cityName.length(); ++i) {
            fieldCity.sendKeys(Character.toString(cityName.charAt(i)));
            wait.withTimeout(50, TimeUnit.MILLISECONDS);
        }

        //todo ask about the initialization of webdriverwait
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(listboxCities));
        WebElement firstCity = listboxCities.findElement(locatorFirstCityOfList);
        wait.until(ExpectedConditions.textToBePresentInElement(firstCity, cityName));
        Assert.assertEquals(cityName, firstCity.getText());
        firstCity.click();
        wait.until(ExpectedConditions.visibilityOf(buttonOk));
        buttonOk.click();
        wait.until(ExpectedConditions.textToBePresentInElement(buttonCity, cityName));
    }

    @Step("Check signIn button text = {text}")
    public void checkSignInButtonText(String text) {
        Assert.assertEquals(text, buttonAuth.getText());
    }

    @Step("Open settings")
    public void openSettings() {
        buttonAuth.click();
        wait.until(ExpectedConditions.visibilityOf(buttonSettings));
        buttonSettings.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locatorSettingsPage));
    }

    @Step("Open electrical toothbrushes page")
    public void openElectricalToothbrushesPage() {
        buttonCatalog.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locatorBeautyAndHygienePage));
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(locatorBeautyAndHygienePage)).build().perform();
        buttonElectricalToothbrushes.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locatorElectricalToothbrushesPage));
    }


}
