package ru.beru.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class SettingsPage {
    WebDriver driver;
    WebDriverWait wait;

    public SettingsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    @FindBy(xpath = "//span[contains(@data-auto,'region-form-opener')]//span[2]")
    private WebElement buttonCity;

    @FindBy(xpath = "//span[contains(@data-auto,'region')]//span[1]//span[1]")
    private WebElement buttonYourCity;

    @Step("Check that the city name in the settings matches the name in the title")
    public void checkCityMatches() {
        Assert.assertEquals(buttonCity.getText(), buttonYourCity.getText());
    }
}
