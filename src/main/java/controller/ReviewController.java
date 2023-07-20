package controller;

import constants.Endpoint;
import io.qameta.allure.Step;
import io.restassured.http.Header;
import io.restassured.response.Response;
import model.reviews.ReviewCreateRequest;
import model.reviews.ReviewCreateResponse;
import util.ApiCall;

public class ReviewController {

  @Step("POST method to create single review")
  public static ReviewCreateResponse createSingleReview(String bootcampId, String token) {
    ReviewCreateRequest reviewCreateRequest = ReviewCreateRequest.getRandomReview();
    Response response = ApiCall.post(Endpoint.addReviewForBootcampById(bootcampId),
        new Header("Authorization", "Bearer " + token), reviewCreateRequest);
    return response.as(ReviewCreateResponse.class);
  }
}
