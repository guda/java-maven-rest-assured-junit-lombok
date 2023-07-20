package integration;

import static util.Hooks.attachLog;
import static util.Hooks.clearLogs;

import enums.TestEnvironment;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/** In order to use hook BeforeAll we need to extend our Test classes with BaseClass. */
public class BaseTest {

  @BeforeAll
  public static void setBaseURL() {
    RestAssured.baseURI = TestEnvironment.BASE_URL.getUrl();
  }

  @BeforeEach
  public void setUp() {
    clearLogs();
  }

  @AfterEach
  public void addLogAttachments() {
    attachLog();
  }
}
