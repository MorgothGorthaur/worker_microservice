package executor.service.factory.webdriverinitializer;

import executor.service.factory.webdriverinitializer.proxy.ProxyProvider;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.WebDriverConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {WebDriverConfig.class})
class ChromeDriverProviderImplTest {
    private static final String GET_USER_AGENT_SCRIPT = "return navigator.userAgent;";
    private static final String HTTP_PROXY = "https://localhost:8008";
    private WebDriverProvider driverProvider;
    @MockBean
    private ProxyProvider proxyProvider;
    @Autowired
    private WebDriverConfig webDriverConfig;


    @BeforeEach
    void setup() {
        DriverService driverService = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(webDriverConfig.getWebDriverExecutable()))
                .build();
        driverProvider = new ChromeDriverProviderImpl(proxyProvider, webDriverConfig, driverService);
    }

    @Test
    void testCreateWithoutProxies() {
        WebDriver driver = driverProvider.create();
        verifyDriverConfiguration(driver);
        driver.close();
    }

    @Test
    void testCreateWithProxies() {
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(HTTP_PROXY);
        ProxyConfigHolder proxyConfigHolder = new ProxyConfigHolder();
        when(proxyProvider.getProxy(eq(proxyConfigHolder))).thenReturn(proxy);
        WebDriver driver = driverProvider.create(proxyConfigHolder);
        verifyDriverConfiguration(driver);
        driver.close();
        verify(proxyProvider, times(1)).getProxy(eq(proxyConfigHolder));
    }

    void verifyDriverConfiguration(WebDriver driver){
        assertThat(driver).isNotNull();
        assertThat(driver).isInstanceOf(ChromeDriver.class);
        ChromeDriver chromeDriver = (ChromeDriver) driver;
        System.out.println(chromeDriver.getCapabilities());
        Object userAgent = chromeDriver.executeScript(GET_USER_AGENT_SCRIPT);
        assertThat(userAgent).isEqualTo(webDriverConfig.getUserAgent());
        Duration implicitWaitTimeout = chromeDriver.manage().timeouts().getImplicitWaitTimeout();
        assertThat(implicitWaitTimeout).isEqualTo(Duration.ofSeconds(webDriverConfig.getImplicitlyWait()));
        Duration pageLoadTimeout = chromeDriver.manage().timeouts().getPageLoadTimeout();
        assertThat(pageLoadTimeout).isEqualTo(Duration.ofMillis(webDriverConfig.getPageLoadTimeout()));
    }
}