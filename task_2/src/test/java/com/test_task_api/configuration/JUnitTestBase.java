package com.test_task_api.configuration;

import org.junit.jupiter.api.BeforeAll;

import com.test_task_api.api.ApiClient;

/**
 * Base class for all the JUnit-based test classes
 */

public class JUnitTestBase {

  protected static ApiClient apiClient;

  @BeforeAll
  public static void loadConfig() throws Throwable {
    apiClient = new ApiClient();
  };

  public ApiClient getApiClient() {
    return apiClient;
  }

}
