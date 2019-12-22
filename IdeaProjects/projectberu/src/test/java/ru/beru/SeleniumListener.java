package ru.beru;

import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverEventListener;
import java.io.File;

public class SeleniumListener implements WebDriverEventListener {
    @Attachment(value = "Screenshot")
    public static byte[] takeScreenshot(WebDriver driver, By by, String name) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].style.border='3px solid red'", driver.findElement(by));

        try {
            Thread.sleep(170);
        }catch (Exception ignored) {
        }
        File screenShotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenShotFile, new File("./target/screenshots/" + name + ".png"));
        } catch (Exception ignored) {
        }

        byte[] screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        jse.executeScript("arguments[0].style.border=''", driver.findElement(by));
        return screen;
    }

    @Attachment(value = "Screenshot")
    public static byte[] takeScreenshot(WebDriver driver, WebElement webElement, String name) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].style.border='3px solid red'", webElement);
        try {
            Thread.sleep(100);
        }catch (Exception e) {
            System.out.println(e);
        }
        File screenShotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenShotFile, new File("./target/screenshots/" + name + ".png"));
        } catch (Exception e) {
            System.out.println(e);
            webElement.getLocation();
        }
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public void beforeAlertAccept(WebDriver webDriver) {

    }

    public void afterAlertAccept(WebDriver webDriver) {

    }

    public void afterAlertDismiss(WebDriver webDriver) {

    }

    public void beforeAlertDismiss(WebDriver webDriver) {

    }

    public void beforeNavigateTo(String s, WebDriver webDriver) {

    }

    public void afterNavigateTo(String s, WebDriver webDriver) {

    }

    public void beforeNavigateBack(WebDriver webDriver) {

    }

    public void afterNavigateBack(WebDriver webDriver) {

    }

    public void beforeNavigateForward(WebDriver webDriver) {

    }

    public void afterNavigateForward(WebDriver webDriver) {

    }

    public void beforeNavigateRefresh(WebDriver webDriver) {

    }

    public void afterNavigateRefresh(WebDriver webDriver) {

    }

    //todo ASK IT
    public void beforeFindBy(By by, WebElement webElement, WebDriver webDriver) {
        try {
            takeScreenshot(webDriver, by, webDriver.findElement(by).getText());
        } catch (Exception ignored) {};
    }

    public void afterFindBy(By by, WebElement webElement, WebDriver webDriver) {

    }

    public void beforeClickOn(WebElement webElement, WebDriver webDriver) {
//        takeScreenshot(webDriver, webElement, webElement.getText());
    }

    public void afterClickOn(WebElement webElement, WebDriver webDriver) {

    }

    public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {

    }

    public void afterChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {

    }

    public void beforeScript(String s, WebDriver webDriver) {

    }

    public void afterScript(String s, WebDriver webDriver) {

    }

    public void onException(Throwable throwable, WebDriver webDriver) {
    }

}
