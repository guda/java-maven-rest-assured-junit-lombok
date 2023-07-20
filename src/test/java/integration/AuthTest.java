package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import constants.Endpoint;
import constants.TagMe;
import constants.UserRole;
import controller.AuthController;
import io.qameta.allure.Epic;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import model.auth.AuthForgotPassRequest;
import model.auth.AuthForgotPassResponse;
import model.auth.AuthLoginRequest;
import model.auth.AuthLoginResponse;
import model.auth.AuthLogoutResponse;
import model.auth.AuthMeResponse;
import model.auth.AuthRegisterRequest;
import model.auth.AuthRegisterResponse;
import model.auth.AuthResetPassRequest;
import model.auth.AuthUpdatePassRequest;
import model.auth.AuthUpdatePassResponse;
import model.auth.UpdateUserRequest;
import model.auth.UpdateUserResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import util.ApiCall;


@Epic("Authentication Epic")
@DisplayName("Auth Test Suite")
//@Feature("Authentication Feature")
public class AuthTest extends BaseTest {

  @Test
//  @Story("Authentication - User Registration")
  @DisplayName("POST to " + Endpoint.AUTH_REGISTER + " works as expected")
  @Timeout(5)
  @Issue("2vrta10")
  @TmsLink("2vrp654")
  @Tags({@Tag(TagMe.AUTH), @Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE), @Tag(TagMe.SANITY)})
  @Severity(SeverityLevel.BLOCKER)
  void authRegister() {
    System.out.println("Current Thread Name: " + Thread.currentThread().getName());

    Response getAuthRegisterResponse = ApiCall.post(Endpoint.AUTH_REGISTER,
        AuthRegisterRequest.getRandomUser(UserRole.PUBLISHER));

    AuthRegisterResponse authRegisterResponse = getAuthRegisterResponse.as(AuthRegisterResponse.class);
    assertEquals(HttpStatus.SC_OK, getAuthRegisterResponse.statusCode());
    assertTrue(authRegisterResponse.success, "Success field was expected to have value TRUE");
    assertFalse(authRegisterResponse.token.isEmpty(), "Field Token in response was empty");
  }

  @Test
  @DisplayName("POST to " + Endpoint.AUTH_LOGIN + " works as expected")
  @Timeout(5)
  @Tags({@Tag(TagMe.AUTH), @Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE)})
  void authLogin() {
    System.out.println("Current Thread Name: " + Thread.currentThread().getName());

    AuthRegisterRequest authRegisterRequest = AuthRegisterRequest.getRandomUser(UserRole.PUBLISHER);
    AuthController.postAuthRegister(authRegisterRequest);

    AuthLoginRequest authLoginRequest = new AuthLoginRequest(authRegisterRequest.email,
        authRegisterRequest.password);
    Response getAuthLoginResponse = ApiCall.post(Endpoint.AUTH_LOGIN, authLoginRequest);

    AuthLoginResponse authLoginResponse = getAuthLoginResponse.as(AuthLoginResponse.class);
    assertEquals(HttpStatus.SC_OK, getAuthLoginResponse.statusCode());
    assertTrue(authLoginResponse.success, "Success field was expected to have value TRUE");
    assertFalse(authLoginResponse.token.isEmpty(), "Field Token in response was empty");
  }

  @Test
  @DisplayName("GET to " + Endpoint.AUTH_ME + " works as expected")
  @Timeout(5)
  @Tags({@Tag(TagMe.AUTH), @Tag(TagMe.REGRESSION), @Tag(TagMe.SANITY)})
  void authMe() {
    System.out.println("Current Thread Name: " + Thread.currentThread().getName());

    AuthRegisterRequest authRegisterRequest = AuthRegisterRequest.getRandomUser(UserRole.PUBLISHER);
    AuthRegisterResponse authRegisterResponse = AuthController.postAuthRegister(
        authRegisterRequest);

    Response getAuthMeResponse = ApiCall.get(Endpoint.AUTH_ME,
        AuthController.getAuthHeader(authRegisterResponse.token));

    AuthMeResponse authMeResponse = getAuthMeResponse.as(AuthMeResponse.class);
    assertEquals(HttpStatus.SC_OK, getAuthMeResponse.statusCode());
    assertTrue(authMeResponse.success, "Success field was expected to have value TRUE");
    assertEquals(authRegisterRequest.role, authMeResponse.data.role);
    assertEquals(authRegisterRequest.name, authMeResponse.data.getName());
    assertEquals(authRegisterRequest.email, authMeResponse.data.getEmail());
  }

  @Test
  @Issue("2wn7efr")
  @Disabled("Existing bug")
  @DisplayName("POST to " + Endpoint.AUTH_FORGOTPASS + " works as expected")
  @Tags({@Tag(TagMe.AUTH), @Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE)})
  void authForgotPassword() {
    AuthRegisterRequest authRegisterRequest = AuthRegisterRequest.getRandomUser(UserRole.USER);
    AuthController.postAuthRegister(authRegisterRequest);

    AuthForgotPassRequest authForgotPassRequest = AuthForgotPassRequest.setEmail(
        authRegisterRequest.getEmail());
    Response getAuthForgotPassResponse = ApiCall.post(Endpoint.AUTH_FORGOTPASS, authForgotPassRequest);

    AuthForgotPassResponse authForgotPassResponse = getAuthForgotPassResponse.as(
        AuthForgotPassResponse.class);
    assertEquals(HttpStatus.SC_OK, getAuthForgotPassResponse.statusCode());
    assertTrue(authForgotPassResponse.success, "Success field was expected to have value TRUE");
    assertFalse(authForgotPassResponse.data.isEmpty(), "Field Token in response was empty");
    assertEquals("Email sent", authForgotPassResponse.getData());
  }

  @Test
  @Issue("2wne6c4")
  @Disabled("we cannot generate passwordToken because " + Endpoint.AUTH_FORGOTPASS + " is not working")
  @DisplayName("PUT to " + Endpoint.AUTH_RESETPASSWORD + "/{id} works as expected")
  @Tags({@Tag(TagMe.AUTH), @Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE)})
  void authResetPassword() {

    //TODO: it is hardcoded for testing purposes, but we need this token from the /forgotpassword endpoint
    String passwordToken = "cd4739d9ae2f1b04299c99e1d4c2bfa0046caf77";

    AuthResetPassRequest authResetPassRequest = AuthResetPassRequest.resetPass();
    Response putAuthResetPassResponse = ApiCall.put(Endpoint.resetPassword(passwordToken), authResetPassRequest);

    AuthForgotPassResponse authResetPassResponse = putAuthResetPassResponse.as(AuthForgotPassResponse.class);
    assertEquals(HttpStatus.SC_OK, putAuthResetPassResponse.statusCode());
    assertTrue(authResetPassResponse.success, "Success field was expected to have value TRUE");
  }

  @Test
  @DisplayName("PUT to " + Endpoint.AUTH_UPDATE_PASS + " works as expected")
  @Tags({@Tag(TagMe.AUTH), @Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE)})
  @Severity(SeverityLevel.BLOCKER)
  void authUpdatePassword() {
    AuthRegisterRequest authRegisterRequest = AuthRegisterRequest.getRandomUser(UserRole.USER);
    AuthRegisterResponse authRegisterResponse = AuthController.postAuthRegister(
        authRegisterRequest);

    AuthUpdatePassRequest authUpdatePassRequest = AuthUpdatePassRequest.setPass(authRegisterRequest.password,
        AuthUpdatePassRequest.builder().newPassword("abcd1234").build().newPassword);

    Response getAuthUpdatePassResponse = ApiCall.put(Endpoint.AUTH_UPDATE_PASS,
        AuthController.getAuthHeader(authRegisterResponse.token), authUpdatePassRequest);

    AuthUpdatePassResponse authUpdatePassResponse = getAuthUpdatePassResponse.as(AuthUpdatePassResponse.class);
    assertEquals(HttpStatus.SC_OK, getAuthUpdatePassResponse.statusCode());
    assertTrue(authUpdatePassResponse.success, "Success field was expected to have value TRUE");

    AuthLoginRequest authLoginRequest = new AuthLoginRequest(authRegisterRequest.email,
        authUpdatePassRequest.newPassword);
    Response getAuthLoginResponse = ApiCall.post(Endpoint.AUTH_LOGIN, authLoginRequest);

    AuthLoginResponse authLoginResponse = getAuthLoginResponse.as(AuthLoginResponse.class);
    assertEquals(HttpStatus.SC_OK, getAuthLoginResponse.statusCode());
    assertTrue(authLoginResponse.success, "Success field was expected to have value TRUE");
  }

  @Test
  @DisplayName("PUT to " + Endpoint.AUTH_UPDATE_USER + " works as expected")
  @Tags({@Tag(TagMe.AUTH), @Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE)})
  @Severity(SeverityLevel.NORMAL)
  void updateUser() {
    UpdateUserRequest updateUserRequest = UpdateUserRequest.updateDetails();

    String token = AuthController.getTokenFromRegisteredUser(UserRole.USER);
    Response putUpdateUser = ApiCall.put(
        Endpoint.AUTH_UPDATE_USER,
        AuthController.getAuthHeader(token),
        updateUserRequest);

    UpdateUserResponse updateUserResponse = putUpdateUser.as(UpdateUserResponse.class);
    assertEquals(HttpStatus.SC_OK, putUpdateUser.statusCode());
    assertTrue(updateUserResponse.success, "Success field was expected to have value TRUE");
    assertEquals(updateUserRequest.getName(), updateUserResponse.getData().getName());
    assertEquals(updateUserRequest.getEmail(), updateUserResponse.getData().getEmail());
  }

  @Test
  @Issue("2wn8kvw")
  @Disabled("Logout is not working properly")
  @DisplayName("GET to " + Endpoint.AUTH_LOGOUT + " works as expected")
  @Timeout(5)
  @Tags({@Tag(TagMe.AUTH), @Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE)})
  void authLogout() {
    String token = AuthController.getTokenFromRegisteredUser(UserRole.USER);
    Response getLogoutResponse = ApiCall.get(Endpoint.AUTH_LOGOUT,
        AuthController.getAuthHeader(token));

    AuthLogoutResponse authLogoutResponse = getLogoutResponse.as(AuthLogoutResponse.class);
    assertEquals(HttpStatus.SC_OK, getLogoutResponse.statusCode());
    assertTrue(authLogoutResponse.success, "Success field was expected to have value TRUE");
  }
}
