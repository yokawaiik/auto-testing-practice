package com.test_task.configuration;

import java.net.URL;
import java.time.Duration;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.test_task.constants.TestConstants;
import com.test_task.constants.UrlConstants;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;

// import ru.stqa.selenium.factory.WebDriverPool;

/**
 * Base class for all the JUnit-based test classes
 */

public class BaseTest {

  protected static URL gridHubUrl;
  protected static String baseUrl;
  protected static Capabilities capabilities;

  protected ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

  protected WebDriver getDriver() {
    return driver.get();
  }

  @BeforeAll
  public static void loadConfig() throws Throwable {
    SuiteConfiguration config = new SuiteConfiguration();
    baseUrl = UrlConstants.BASE;
    if (config.hasProperty("grid.url") && !"".equals(config.getProperty("grid.url"))) {
      gridHubUrl = new URL(config.getProperty("grid.url"));
    }
    capabilities = config.getCapabilities();

  };

  @BeforeEach
  public void initDriver() throws Throwable {
    SuiteConfiguration config = new SuiteConfiguration();
    WebDriverManager.chromedriver().setup();
    ChromeOptions options = new ChromeOptions();

    options.setBrowserVersion(config.appWebdriverChromeVersion);
    options.addArguments("--remote-allow-origins=*");
    options.addArguments("--disable-extensions");
    options.addArguments("--disable-dev-shm-usage");

    if (config.appWebdriverModeHeadless == true) {
      options.addArguments("--headless");
    }
    driver.set(new ChromeDriver(options));

    // wait for elements loading
    driver.get().manage().timeouts().implicitlyWait(Duration.ofMillis(TestConstants.IMPLICITLY_WAIT_IN_MILLISECONDS));
    driver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestConstants.PAGE_LOAD_TIMEOUT_IN_SECONDS));
  }

  @AfterEach
  void teardown() {
    if (driver != null) {
      getDriver().quit();

    }
  }

  public boolean isElementPresent(By by) {
    try {
      getDriver().findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }
}
