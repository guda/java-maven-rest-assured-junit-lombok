package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import constants.Endpoint;
import constants.TagMe;
import constants.UserRole;
import controller.AuthController;
import controller.BootcampController;
import controller.ReviewController;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.http.Header;
import io.restassured.response.Response;
import java.sql.Timestamp;
import java.util.Random;
import model.bootcamps.BootcampsResponse;
import model.reviews.ReviewCreateRequest;
import model.reviews.ReviewCreateResponse;
import model.reviews.ReviewDeleteResponse;
import model.reviews.ReviewNegativeResponse;
import model.reviews.ReviewResponse;
import model.reviews.ReviewsForBootcampResponse;
import model.reviews.ReviewsResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import util.ApiCall;

@Epic("Reviews Epic")
@DisplayName("Reviews Test Suite")
public class ReviewTest extends BaseTest {

  @Test
  @DisplayName("GET to " + Endpoint.REVIEW + "/{{id}} works as expected")
  @Timeout(5)
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SANITY), @Tag(TagMe.SMOKE)})
  void getSingleReview() {
    BootcampsResponse bootcampsResponse = BootcampController.getAllBootcamps();

    ReviewCreateResponse reviewCreateResponse = ReviewController.createSingleReview(
        bootcampsResponse.data.get(new Random().nextInt(bootcampsResponse.data.size())).id,
        AuthController.getTokenFromRegisteredUser(UserRole.USER));

    Response getSingleReviewsResponse = ApiCall.get(Endpoint.getReviewById(reviewCreateResponse.data.id));

    ReviewResponse reviewResponse = getSingleReviewsResponse.as(ReviewResponse.class);
    assertEquals(HttpStatus.SC_OK, getSingleReviewsResponse.statusCode());
    assertFalse(reviewResponse.data.user.isEmpty());
    assertEquals(reviewCreateResponse.data.id, reviewResponse.data.id);
    assertTrue(reviewResponse.success, "Success field was expected to have value TRUE");
  }

  @Test
  @DisplayName("GET to " + Endpoint.REVIEW + "works as expected")
  @Timeout(5)
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE)})
  void getAllReviews() {
    Response getAllReviewsResponse = ApiCall.get(Endpoint.REVIEW);

    ReviewsResponse reviewsResponse = getAllReviewsResponse.as(ReviewsResponse.class);
    assertEquals(HttpStatus.SC_OK, getAllReviewsResponse.statusCode());
    assertFalse(reviewsResponse.data.get(new Random().nextInt(reviewsResponse.data.size())).user.isEmpty(),
        "User field was expected to not be empty.");
    assertTrue(reviewsResponse.success, "Success field was expected to have value TRUE");
  }

  @Test
  @DisplayName("POST to " + Endpoint.BOOTCAMP + "/{{id}}/reviews works as expected")
  @Timeout(5)
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE)})
  void createSingleReviewForBootcamp() {
    BootcampsResponse bootcampsResponse = BootcampController.getAllBootcamps();

    String token =AuthController.getTokenFromRegisteredUser(UserRole.USER);

    ReviewCreateRequest reviewCreateRequest = ReviewCreateRequest.getRandomReview();
    int index = new Random().nextInt(bootcampsResponse.data.size());

    Response postSingleReview = ApiCall.post(
        Endpoint.addReviewForBootcampById(bootcampsResponse.data.get(index).id),
        AuthController.getAuthHeader(token),
        reviewCreateRequest);

    ReviewCreateResponse reviewCreateResponse = postSingleReview.as(ReviewCreateResponse.class);
    assertEquals(HttpStatus.SC_CREATED, postSingleReview.statusCode());
    assertEquals(reviewCreateResponse.data.bootcamp, bootcampsResponse.data.get(index).id);
    assertFalse(reviewCreateResponse.data.user.isEmpty(), "User field was expected to not be empty.");
    assertTrue(reviewCreateResponse.success, "Success field was expected to have value TRUE");
  }

  @Test
  @DisplayName("DELETE to " + Endpoint.REVIEW + "/{{id}} works as expected")
  @Timeout(5)
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE)})
  @Severity(SeverityLevel.NORMAL)
  void deleteReview() {
    String token = AuthController.getTokenFromRegisteredUser(UserRole.USER);

    BootcampsResponse bootcampsResponse = BootcampController.getAllBootcamps();

    ReviewCreateResponse reviewCreateResponse = ReviewController.createSingleReview(
        bootcampsResponse.data.get(new Random().nextInt(bootcampsResponse.data.size())).id,
        token);

    Response deleteReview = ApiCall.delete(Endpoint.deleteReview(reviewCreateResponse.data.id),
       AuthController.getAuthHeader(token));

    ReviewDeleteResponse reviewDeleteResponse = deleteReview.as(ReviewDeleteResponse.class);
    assertEquals(HttpStatus.SC_OK, deleteReview.statusCode());
    assertTrue(reviewDeleteResponse.success, "Success field was expected to have value TRUE");
    assertTrue(reviewDeleteResponse.data.isObject(), "Data field was expected to be object.");
    assertTrue(reviewDeleteResponse.data.isEmpty(), "Data field was expected to be empty.");
  }

  @Test
  @DisplayName("Negative test DELETE to " + Endpoint.REVIEW + "/{{id}} works as expected")
  @Timeout(5)
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE)})
  @Severity(SeverityLevel.BLOCKER)
  void deleteReviewNegative() {
    BootcampsResponse bootcampsResponse = BootcampController.getAllBootcamps();

    ReviewCreateResponse reviewCreateResponse = ReviewController.createSingleReview(
        bootcampsResponse.data.get(new Random().nextInt(bootcampsResponse.data.size())).id,
        AuthController.getTokenFromRegisteredUser(UserRole.USER));

    Response deleteReviewNegative = ApiCall.delete(
        Endpoint.deleteReview(reviewCreateResponse.data.id),
        new Header("Authorization", "Bearer " + ""));

    ReviewNegativeResponse deleteReviewNegativeResponse = deleteReviewNegative.as(ReviewNegativeResponse.class);
    assertEquals(HttpStatus.SC_UNAUTHORIZED, deleteReviewNegative.statusCode());
    assertEquals(deleteReviewNegativeResponse.error, "Not authorized to access this route");
    assertFalse(deleteReviewNegativeResponse.success, "Success field was expected to have value FALSE");
  }

  @Test
  @DisplayName("PUT to " + Endpoint.REVIEW + "/{{id}}/reviews works as expected")
  @Timeout(5)
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE)})
  void updateReview() {
    String token = AuthController.getTokenFromRegisteredUser(UserRole.USER);
    BootcampsResponse bootcampsResponse = BootcampController.getAllBootcamps();

    ReviewCreateResponse reviewCreateResponse = ReviewController.createSingleReview(
        bootcampsResponse.data.get(new Random().nextInt(bootcampsResponse.data.size())).id,
        token);

    Response putSingleReview = ApiCall.put(Endpoint.getReviewById(reviewCreateResponse.data.id),
      AuthController.getAuthHeader(token), ReviewCreateRequest
            .builder()
            .title(ReviewCreateRequest.getRandomReview().getTitle() + new Timestamp(System.currentTimeMillis()))
            .build());

    ReviewCreateResponse reviewUpdateResponse = putSingleReview.as(ReviewCreateResponse.class);
    assertEquals(HttpStatus.SC_OK, putSingleReview.statusCode());
    assertEquals(reviewUpdateResponse.data.bootcamp, reviewCreateResponse.data.bootcamp);
    assertFalse(reviewUpdateResponse.data.user.isEmpty(), "User field was expected to not be empty.");
    assertTrue(reviewUpdateResponse.success, "Success field was expected to have value TRUE");
  }

  @Test
  @DisplayName("Negative test PUT to " + Endpoint.REVIEW + "/{{id}}/reviews works as expected")
  @Timeout(5)
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE)})
  void updateReviewNegative() {
    String token = AuthController.getTokenFromRegisteredUser(UserRole.USER);

    BootcampsResponse bootcampsResponse = BootcampController.getAllBootcamps();

    ReviewCreateRequest reviewCreateRequest = ReviewCreateRequest.getRandomReview();

    ReviewCreateResponse reviewCreateResponse = ReviewController.createSingleReview(
        bootcampsResponse.data.get(new Random().nextInt(bootcampsResponse.data.size())).id,
        token);

    Response reviewNoAuthResponse = ApiCall.put(Endpoint.getReviewById(reviewCreateResponse.data.id),
        new Header("Authorization", "Bearer " + ""), ReviewCreateRequest
            .builder()
            .title(reviewCreateRequest.getTitle() + new Timestamp(System.currentTimeMillis()))
            .build());

    ReviewNegativeResponse reviewUpdateNoAuthResponse = reviewNoAuthResponse.as(ReviewNegativeResponse.class);
    assertEquals(HttpStatus.SC_UNAUTHORIZED, reviewNoAuthResponse.statusCode());
    assertFalse(reviewUpdateNoAuthResponse.success, "Success field was expected to have value FALSE");

    Response reviewUpdateNegative = ApiCall.put(Endpoint.getReviewById(reviewCreateResponse.data.id),
        AuthController.getAuthHeader(token), ReviewCreateRequest
            .builder()
            .title(reviewCreateRequest.getTitle() + new Timestamp(System.currentTimeMillis()))
            .rating(20)
            .build());

    ReviewNegativeResponse reviewUpdateNegativeResponse = reviewUpdateNegative.as(ReviewNegativeResponse.class);
    assertEquals(HttpStatus.SC_BAD_REQUEST, reviewUpdateNegative.statusCode());
    assertFalse(reviewUpdateNegativeResponse.success, "Success field was expected to have value FALSE");
  }

  @Test
  @DisplayName("GET to " + Endpoint.BOOTCAMP + "/{{id}}/reviews works as expected")
  @Timeout(5)
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE)})
  void getReviewsForBootcamp() {

    BootcampsResponse bootcampsResponse = BootcampController.getAllBootcamps();
    int bootcampIndex = new Random().nextInt(bootcampsResponse.data.size());

    ReviewController.createSingleReview(
        bootcampsResponse.data.get(bootcampIndex).id,
        AuthController.getTokenFromRegisteredUser(UserRole.USER));

    Response getReviewsForBootcampResponse = ApiCall.get(
        Endpoint.getReviewsForBootcampById(bootcampsResponse.data.get(bootcampIndex).id));

    ReviewsForBootcampResponse reviewsForBootcampResponse = getReviewsForBootcampResponse.as(
        ReviewsForBootcampResponse.class);

    assertEquals(HttpStatus.SC_OK, getReviewsForBootcampResponse.statusCode());
    assertEquals(
        reviewsForBootcampResponse.data.get(new Random().nextInt(reviewsForBootcampResponse.count)).bootcamp,
        bootcampsResponse.data.get(bootcampIndex).id);
    assertFalse(reviewsForBootcampResponse.data.isEmpty(), "Data was expected to not be empty.");
    assertTrue(reviewsForBootcampResponse.success, "Success field was expected to have value TRUE");
  }
}
