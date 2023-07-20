package controller;

import constants.Endpoint;
import io.qameta.allure.Step;
import io.restassured.http.Header;
import io.restassured.response.Response;
import model.bootcamps.BootcampCreateResponse;
import model.courses.CourseCreateRequest;
import model.courses.CourseCreateResponse;
import util.ApiCall;

public class CourseController {

  @Step("POST method to create course for bootcamp")
  public static CourseCreateResponse createCourseForBootcamp(String token,
      BootcampCreateResponse bootcampCreateResponse, CourseCreateRequest courseCreateRequest) {
    Response response = ApiCall.post(Endpoint.addCourseForBootcampById(bootcampCreateResponse.data.id),
        new Header("Authorization", "Bearer " + token), courseCreateRequest);
    return response.as(CourseCreateResponse.class);
  }
}
