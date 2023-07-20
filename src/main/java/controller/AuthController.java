package controller;

import constants.Endpoint;
import io.qameta.allure.Step;
import io.restassured.http.Header;
import io.restassured.response.Response;
import model.auth.AuthRegisterRequest;
import model.auth.AuthRegisterResponse;
import util.ApiCall;

public class AuthController {

  @Step("POST method to register user")
  public static AuthRegisterResponse postAuthRegister(AuthRegisterRequest authRegisterRequest) {
    Response response = ApiCall.post(Endpoint.AUTH_REGISTER, authRegisterRequest);
    return response.as(AuthRegisterResponse.class);
  }

  @Step("POST method to get token from register user")
  public static String getTokenFromRegisteredUser(String role) {
    AuthRegisterResponse authRegisterResponse =
        AuthController.postAuthRegister(AuthRegisterRequest.getRandomUser(role));
    return authRegisterResponse.getToken();
  }

  public static Header getAuthHeader(String token) {
    return new Header("Authorization", "Bearer " + token);
  }
}
