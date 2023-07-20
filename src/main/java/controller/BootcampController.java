package controller;

import constants.Endpoint;
import io.qameta.allure.Step;
import io.restassured.http.Header;
import io.restassured.response.Response;
import java.util.Random;
import model.bootcamps.BootcampCreateRequest;
import model.bootcamps.BootcampCreateResponse;
import model.bootcamps.BootcampResponse;
import model.bootcamps.BootcampsResponse;
import util.ApiCall;

public class BootcampController {

  @Step("POST method to create bootcamp")
  public static BootcampCreateResponse createBootcamp(String token) {
    BootcampCreateRequest bootcampCreateRequest = BootcampCreateRequest.buildRandomBootcamp();
    Response response = ApiCall.post(Endpoint.BOOTCAMP,
        new Header("Authorization", "Bearer " + token), bootcampCreateRequest);
    return response.as(BootcampCreateResponse.class);
  }

  @Step("GET method to get all created bootcamps")
  public static BootcampsResponse getAllBootcamps() {
    Response response = ApiCall.get(Endpoint.BOOTCAMP);
    return response.as(BootcampsResponse.class);
  }

  @Step("GET method to get random bootcamps")
  public static BootcampResponse getRandomBootcamp() {
    BootcampsResponse allBootcamps = ApiCall.get(Endpoint.BOOTCAMP).as(BootcampsResponse.class);

    Response response = ApiCall.get(
        Endpoint.getBootcampById(allBootcamps.data.get(new Random().nextInt(allBootcamps.data.size())).id));
    return response.as(BootcampResponse.class);
  }
}
