package com.test_task.pages;

import java.time.Duration;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.test_task.constants.TestConstants;

/**
 * Abstract class representation of a Page in the UI. Page object pattern
 */
public abstract class Page {

  protected WebDriver driver;
  public String pageUrl;

  public Page(WebDriver driver) {
    this.driver = driver;
    pageUrl = driver.getCurrentUrl();
  }

  public String getTitle() {
    return driver.getTitle();
  }

  protected boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  public void waitForAlertDialog() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(TestConstants.WAIT_INTERVAL_IN_SECONDS));
    wait.pollingEvery(Duration.ofMillis(TestConstants.POLLING_EVERY));
    wait.until(ExpectedConditions.alertIsPresent());
  }

  public Optional<WebElement> waitWhileElementToBeClickable(By by) {
    for (int index = 0; index < TestConstants.WAITING_ATTEMPTS_COUNT; index++) {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(TestConstants.WAIT_INTERVAL_IN_SECONDS));
      wait.pollingEvery(Duration.ofMillis(TestConstants.POLLING_EVERY));
      final var element = wait.until(ExpectedConditions.elementToBeClickable(by));
      if (element != null) {
        return Optional.of(element);
      }
    }
    return null;
  }

}
