package ru.beru.tests;

import org.testng.annotations.Test;
import ru.beru.dataProviders.changeCityTestDataProvider;
import ru.beru.WebDriverSettings;
import ru.beru.pages.HomePage;
import ru.beru.pages.PassportPage;
import ru.beru.pages.SettingsPage;

public class ChangeCity extends WebDriverSettings {

    @Test(dataProvider = "Cities-provider", dataProviderClass = changeCityTestDataProvider.class)
    public void changeCity(String defaultCity, String cityName) {
        String login = "alyawinter";
        String password = "winter2019";
        String buttonText = "Мой профиль";

        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.checkCity(defaultCity);
        homePage.changeCity(cityName);
        homePage.checkCity(cityName);
        homePage.openPassportPage();

        PassportPage passportPage = new PassportPage(driver);
        passportPage.fillLoginAndEnter(login);
        passportPage.fillPasswordAndEnter(password);
        homePage.checkSignInButtonText(buttonText);
        homePage.openSettings();

        SettingsPage settingsPage = new SettingsPage(driver);
        settingsPage.checkCityMatches();
    }
}
