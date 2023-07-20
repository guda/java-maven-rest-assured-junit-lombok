package integration;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import constants.Endpoint;
import constants.TagMe;
import constants.UserRole;
import controller.AuthController;
import controller.BootcampController;
import io.qameta.allure.Epic;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import java.io.File;
import java.sql.Timestamp;
import model.bootcamps.BootcampCreateRequest;
import model.bootcamps.BootcampCreateResponse;
import model.bootcamps.BootcampDeleteResponse;
import model.bootcamps.BootcampNegativeResponse;
import model.bootcamps.BootcampResponse;
import model.bootcamps.BootcampUpdateRequest;
import model.bootcamps.BootcampUpdateResponse;
import model.bootcamps.BootcampsResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import util.ApiCall;

@Epic("Bootcamps Epic")
@DisplayName("Bootcamp Test Suite")
public class BootcampTest extends BaseTest {

  @Test
  @DisplayName("GET to " + Endpoint.BOOTCAMP + " works as expected")
  @Timeout(5)
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE), @Tag(TagMe.SANITY)})
  @Severity(SeverityLevel.BLOCKER)
  void getAllBootcamps() {
    Response getAllBootcampsResponse = ApiCall.get(Endpoint.BOOTCAMP);

    BootcampsResponse bootcampsResponse = getAllBootcampsResponse.as(BootcampsResponse.class);
    assertEquals(HttpStatus.SC_OK, getAllBootcampsResponse.statusCode());
    assertTrue(bootcampsResponse.success, "Success field was expected to have value TRUE");
    assertFalse(bootcampsResponse.data.get(0).id.isEmpty(),
        "ID field was expected to not be empty.");
  }

  @Test
  @DisplayName("GET to " + Endpoint.BOOTCAMP + "/{{id}} works as expected")
  @Timeout(5)
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE), @Tag(TagMe.SANITY)})
  @Severity(SeverityLevel.BLOCKER)
  void getSingleBootcamp() {
    BootcampCreateResponse bootcampCreateResponse = BootcampController.createBootcamp(
        AuthController.getTokenFromRegisteredUser(UserRole.PUBLISHER));

    Response getSingleBootcampResponse = ApiCall.get(
        Endpoint.addBootcampById(bootcampCreateResponse.data.id));

    BootcampResponse bootcampResponse = getSingleBootcampResponse.as(BootcampResponse.class);
    assertEquals(HttpStatus.SC_OK, getSingleBootcampResponse.statusCode());
    assertTrue(bootcampResponse.success, "Success field was expected to have value TRUE");
    assertFalse(bootcampResponse.data.id.isEmpty(), "ID field was expected to not be empty.");
  }

  @Test
  @DisplayName("POST to " + Endpoint.BOOTCAMP + " works as expected")
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE), @Tag(TagMe.SANITY)})
  @Severity(SeverityLevel.BLOCKER)
  void createBootcamp() {
    String token = AuthController.getTokenFromRegisteredUser(UserRole.PUBLISHER);
    BootcampCreateRequest bootcampCreateRequest = BootcampCreateRequest.buildRandomBootcamp();
    Response postBootcampResponse = ApiCall.post(Endpoint.BOOTCAMP,
        AuthController.getAuthHeader(token),
        bootcampCreateRequest);

    BootcampCreateResponse bootcampCreateResponse = postBootcampResponse.as(
        BootcampCreateResponse.class);
    assertEquals(HttpStatus.SC_CREATED, postBootcampResponse.statusCode());
    assertTrue(bootcampCreateResponse.success, "Success field was expected to have value TRUE");
    assertFalse(bootcampCreateResponse.data.id.isEmpty(), "Field ID in response was empty");
  }

  @Test
  @DisplayName("DELETE to " + Endpoint.BOOTCAMP + " works as expected")
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE), @Tag(TagMe.SANITY)})
  @Severity(SeverityLevel.BLOCKER)
  void deleteBootcamp() {
    String token = AuthController.getTokenFromRegisteredUser(UserRole.PUBLISHER);

    BootcampCreateResponse bootcampCreateResponse = BootcampController.createBootcamp(token);

    Response deleteBootcampResponse = ApiCall.delete(
        Endpoint.addBootcampById(bootcampCreateResponse.data.id),
        AuthController.getAuthHeader(token));

    BootcampDeleteResponse bootcampDeleteResponse = deleteBootcampResponse.as(
        BootcampDeleteResponse.class);
    assertEquals(HttpStatus.SC_OK, deleteBootcampResponse.statusCode());
    assertTrue(bootcampDeleteResponse.success, "Success field was expected to have value TRUE");
    assertTrue(bootcampDeleteResponse.data.isObject(), "Data field was expected to be object.");
    assertTrue(bootcampDeleteResponse.data.isEmpty(), "Data field was expected to be empty.");
  }

  @Test
  @DisplayName("PUT to " + Endpoint.BOOTCAMP + " works as expected")
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE), @Tag(TagMe.SANITY)})
  @Severity(SeverityLevel.BLOCKER)
  void updateBootcamp() {
    String token = AuthController.getTokenFromRegisteredUser(UserRole.PUBLISHER);

    BootcampCreateRequest bootcampCreateRequest = BootcampCreateRequest.buildRandomBootcamp();
    BootcampCreateResponse bootcampCreateResponse = BootcampController.createBootcamp(token);

    Response putBootcampResponse = ApiCall.put(
        Endpoint.addBootcampById(bootcampCreateResponse.data.id),
        AuthController.getAuthHeader(token), BootcampUpdateRequest
            .builder()
            .name(bootcampCreateRequest.getName().substring(0, 5) + new Timestamp(
                System.currentTimeMillis()))
            .description(
                bootcampCreateRequest.getDescription().substring(0, 5) + new Timestamp(
                    System.currentTimeMillis()))
            .build());

    BootcampUpdateResponse bootcampUpdateResponse = putBootcampResponse.as(
        BootcampUpdateResponse.class);
    assertEquals(HttpStatus.SC_OK, putBootcampResponse.statusCode());
    assertTrue(bootcampUpdateResponse.success, "Success field was expected to have value TRUE");
  }

  @Test
  @DisplayName("Negative test PUT to " + Endpoint.BOOTCAMP + " works as expected")
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE), @Tag(TagMe.SANITY)})
  @Severity(SeverityLevel.BLOCKER)
  void updateBootcampNegative() {
    String token = AuthController.getTokenFromRegisteredUser(UserRole.PUBLISHER);

    BootcampCreateResponse bootcampCreateResponse = BootcampController.createBootcamp(token);

    Response putBootcampResponse = ApiCall.put(
        Endpoint.addBootcampById(bootcampCreateResponse.data.id),
        AuthController.getAuthHeader(token),
        BootcampUpdateRequest.builder().website("google.com").build());

    BootcampNegativeResponse bootcampNegativeResponse = putBootcampResponse.as(
        BootcampNegativeResponse.class);
    assertEquals(HttpStatus.SC_BAD_REQUEST, putBootcampResponse.statusCode());
    assertFalse(bootcampNegativeResponse.success, "Success field was expected to have value FALSE");
  }

  @Test
  @DisplayName("Negative test DELETE to " + Endpoint.BOOTCAMP + " works as expected")
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE), @Tag(TagMe.SANITY)})
  void deleteBootcampNegative() {
    String token = AuthController.getTokenFromRegisteredUser(UserRole.PUBLISHER);

    BootcampCreateResponse bootcampCreateResponse = BootcampController.createBootcamp(token);

    Response deleteNoAuthResponse = ApiCall.delete(
        Endpoint.addBootcampById(bootcampCreateResponse.data.id),
        new Header("Authorization", "Bearer " + ""));

    assertEquals(HttpStatus.SC_UNAUTHORIZED, deleteNoAuthResponse.statusCode());
    assertFalse(deleteNoAuthResponse.as(BootcampNegativeResponse.class).success,
        "Success field was expected to have value FALSE");

    ApiCall.delete(Endpoint.addBootcampById(bootcampCreateResponse.data.id),
        AuthController.getAuthHeader(token));

    Response deleteBootcampNegative = ApiCall.delete(
        Endpoint.addBootcampById(bootcampCreateResponse.data.id),
        AuthController.getAuthHeader(token));

    assertEquals(HttpStatus.SC_NOT_FOUND, deleteBootcampNegative.statusCode());
    assertFalse(deleteBootcampNegative.as(BootcampNegativeResponse.class).success,
        "Success field was expected to have value FALSE");
  }

  @Test
  @Disabled("Endpoint for uploading photo is not working")
  @Issue("2wv0wug")
  @DisplayName("PUT to " + Endpoint.BOOTCAMP + " works as expected")
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE), @Tag(TagMe.SANITY)})
  @Severity(SeverityLevel.CRITICAL)
  void uploadBootcamp() {
    String token = AuthController.getTokenFromRegisteredUser(UserRole.PUBLISHER);

    BootcampCreateResponse bootcampCreateResponse = BootcampController.createBootcamp(token);

    //TODO: See what kind of response you get, after endpoint is fixed. Adapt the test accordingly
    Response putBootcampResponse = given()
        .contentType(ContentType.MULTIPART)
        .header(AuthController.getAuthHeader(token))
        .multiPart(new File("src/test/resources/data/orange-flower.jpeg"))
        .put(Endpoint.uploadPhoto(bootcampCreateResponse.data.id));

    BootcampUpdateResponse bootcampUpdateResponse = putBootcampResponse.as(
        BootcampUpdateResponse.class);
    assertEquals(HttpStatus.SC_OK, putBootcampResponse.statusCode());
    assertTrue(bootcampUpdateResponse.success, "Success field was expected to have value TRUE");
  }
}
