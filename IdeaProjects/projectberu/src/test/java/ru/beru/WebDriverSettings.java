package ru.beru;

import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;

public class WebDriverSettings {
    public static ChromeDriver chromeDriver;
    public static EventFiringWebDriver driver;
    public static WebDriverWait wait;
    public static SeleniumListener listener;

    @BeforeMethod
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver","C:/webdriver/chromedriver.exe");
        chromeDriver = new ChromeDriver();
        driver = new EventFiringWebDriver(chromeDriver);
        listener = new SeleniumListener();
        wait = new WebDriverWait(chromeDriver, 10);
        driver.register(listener);
        driver.manage().window().fullscreen();
    }

    @AfterMethod
    protected void finish() {

        driver.quit();
    }

    @AfterMethod
    public void catchFailure(ITestResult result) throws IOException {
        if (!result.isSuccess()) {
            takeScreenshot(result.getName());
        }
    }

    @Attachment(value = "Screenshot")
    public byte[] takeScreenshot(String name) throws IOException {
        File screenShotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenShotFile, new File("./target/screenshots/" + name + ".png"));
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}
