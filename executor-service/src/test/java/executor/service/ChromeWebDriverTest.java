package executor.service;

import executor.service.factory.webdriverinitializer.ChromeDriverProviderImpl;
import executor.service.factory.webdriverinitializer.WebDriverProvider;
import executor.service.factory.webdriverinitializer.proxy.ProxyProviderImpl;
import executor.service.model.WebDriverConfigDto;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@ContextConfiguration(classes = WebDriverConfigDto.class)
class ChromeWebDriverTest {

    private WebDriverProvider driverProvider;
    @Autowired
    private WebDriverConfigDto webDriverConfigDto;

    @BeforeEach
    void setup() {
        driverProvider = new ChromeDriverProviderImpl(new ProxyProviderImpl(), webDriverConfigDto);
    }

    @Test
    void testNavigateToCern() throws InterruptedException {
        WebDriver webDriver = driverProvider.create();
        webDriver.get("http://info.cern.ch");
        TimeUnit.SECONDS.sleep(5);
        webDriver.quit();
    }

    @Test
    void testClickElementByXPath() throws InterruptedException {
        WebDriver webDriver = driverProvider.create();
        webDriver.get("http://info.cern.ch");

        WebElement element = webDriver.findElement(By.xpath("/html/body/ul/li[1]/a"));
        TimeUnit.SECONDS.sleep(2);
        element.click();
        TimeUnit.SECONDS.sleep(5);
        webDriver.quit();
    }

    @Test
    void testGoogleSearch() throws InterruptedException {
        WebDriver webDriver = driverProvider.create();
        webDriver.get("https://www.google.com/");

        WebElement element = webDriver.findElement(By.name("q"));

        TimeUnit.SECONDS.sleep(2);
        element.sendKeys("Test");
        TimeUnit.SECONDS.sleep(2);
        element.sendKeys(Keys.ENTER);
        TimeUnit.SECONDS.sleep(5);
        webDriver.quit();
    }
}