package integration;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import constants.Endpoint;
import constants.TagMe;
import constants.UserRole;
import controller.AuthController;
import controller.BootcampController;
import controller.CourseController;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import java.sql.Timestamp;
import java.util.Random;
import model.bootcamps.BootcampCreateResponse;
import model.bootcamps.BootcampResponse;
import model.courses.CourseCreateRequest;
import model.courses.CourseCreateResponse;
import model.courses.CourseDeleteResponse;
import model.courses.CourseResponse;
import model.courses.CoursesForBootcampResponse;
import model.courses.CoursesResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import util.ApiCall;

@Epic("Courses Epic")
@DisplayName("Courses Test Suite")
public class CourseTest extends BaseTest {

  @Test
  @DisplayName("GET to /courses works as expected")
  @Timeout(5)
  @Tags({@Tag("regression"), @Tag("smoke")})
  void getAllCourse() {
    Response getAllCoursesResponse = ApiCall.get(Endpoint.COURSE);

    CoursesResponse coursesResponse = getAllCoursesResponse.as(CoursesResponse.class);
    assertEquals(HttpStatus.SC_OK, getAllCoursesResponse.statusCode());
    assertFalse(coursesResponse.data.get(new Random().nextInt(coursesResponse.data.size())).user.isEmpty(),
        "User field was expected to not be empty.");
    assertTrue(coursesResponse.success, "Success field was expected to have value TRUE");
    assertEquals(coursesResponse.data.size(), coursesResponse.count);
  }

  @Test
  @DisplayName("GET to courses/{{id}} works as expected")
  @Timeout(5)
  @Tags({@Tag("regression"), @Tag("smoke")})
  void getSingleCourse() {
    String token = AuthController.getTokenFromRegisteredUser(UserRole.PUBLISHER);

    BootcampCreateResponse bootcampCreateResponse = BootcampController.createBootcamp(token);

    CourseCreateRequest courseCreateRequest = CourseCreateRequest.getRandomCourse();
    CourseCreateResponse courseCreateResponse = CourseController.createCourseForBootcamp(token,
        bootcampCreateResponse, courseCreateRequest);

    Response getSingleCourseResponse = ApiCall.get(Endpoint.getCourseById(courseCreateResponse.data.id));

    CourseResponse courseResponse = getSingleCourseResponse.as(CourseResponse.class);
    assertEquals(HttpStatus.SC_OK, getSingleCourseResponse.statusCode());
    assertFalse(courseResponse.data.user.isEmpty(), "User field was expected to not be empty.");
    assertTrue(courseResponse.success, "Success field was expected to have value TRUE");
  }

  @Test
  @DisplayName("POST to " + Endpoint.BOOTCAMP + " works as expected")
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE), @Tag(TagMe.SANITY)})
  @Severity(SeverityLevel.BLOCKER)
  void createCourse() {
    String token = AuthController.getTokenFromRegisteredUser(UserRole.PUBLISHER);

    BootcampCreateResponse bootcampCreateResponse = BootcampController.createBootcamp(token);

    CourseCreateRequest courseCreateRequest = CourseCreateRequest.getRandomCourse();
    Response postCourseResponse = ApiCall.post(Endpoint.addCourseForBootcampById(bootcampCreateResponse.data.id),
        AuthController.getAuthHeader(token), courseCreateRequest);

    CourseCreateResponse courseCreateResponse = postCourseResponse.as(CourseCreateResponse.class);

    assertEquals(HttpStatus.SC_OK, postCourseResponse.statusCode());
    assertTrue(courseCreateResponse.success, "Success field was expected to have value TRUE");
  }

  @Test
  @DisplayName("PUT to " + Endpoint.COURSE + "/{id} works as expected")
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE), @Tag(TagMe.SANITY)})
  @Severity(SeverityLevel.BLOCKER)
  void updateCourse() {
    String token = AuthController.getTokenFromRegisteredUser(UserRole.PUBLISHER);

    BootcampCreateResponse bootcampCreateResponse = BootcampController.createBootcamp(token);

    CourseCreateRequest courseCreateRequest = CourseCreateRequest.getRandomCourse();
    CourseCreateResponse courseCreateResponse = CourseController.createCourseForBootcamp(token,
        bootcampCreateResponse, courseCreateRequest);

    Response putCourseUpdateResponse = ApiCall.put(Endpoint.updateCourse(courseCreateResponse.data.id),
        AuthController.getAuthHeader(token), CourseCreateRequest
            .builder()
            .title(courseCreateRequest.title + new Timestamp(System.currentTimeMillis()))
            .description(courseCreateRequest.getDescription() + new Timestamp(System.currentTimeMillis()))
            .build());

    CourseCreateResponse courseUpdateResponse = putCourseUpdateResponse.as(CourseCreateResponse.class);
    assertEquals(HttpStatus.SC_OK, putCourseUpdateResponse.statusCode());
    assertTrue(courseUpdateResponse.success, "Success field was expected to have value TRUE");
  }

  @Test
  @DisplayName("DELETE to " + Endpoint.COURSE + "/{id} works as expected")
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE), @Tag(TagMe.SANITY)})
  @Severity(SeverityLevel.BLOCKER)
  void deleteCourse() {
    String token = AuthController.getTokenFromRegisteredUser(UserRole.PUBLISHER);

    BootcampCreateResponse bootcampCreateResponse = BootcampController.createBootcamp(token);

    CourseCreateRequest courseCreateRequest = CourseCreateRequest.getRandomCourse();
    CourseCreateResponse courseCreateResponse = CourseController.createCourseForBootcamp(token,
        bootcampCreateResponse, courseCreateRequest);

    Response deleteCourseResponse = ApiCall.delete(Endpoint.updateCourse(courseCreateResponse.data.id),
        AuthController.getAuthHeader(token));

    CourseDeleteResponse courseDeleteResponse = deleteCourseResponse.as(CourseDeleteResponse.class);
    assertEquals(HttpStatus.SC_OK, deleteCourseResponse.statusCode());
    assertTrue(courseDeleteResponse.success, "Success field was expected to have value TRUE");
  }

  @Test
  @DisplayName("GET Courses for Bootcamp")
  @Tags({@Tag(TagMe.REGRESSION), @Tag(TagMe.SMOKE), @Tag(TagMe.SANITY)})
  @Severity(SeverityLevel.CRITICAL)
  void getCoursesForBootcamp() {
    BootcampResponse bootcampResponse = BootcampController.getRandomBootcamp();

    Response getCoursesForBootcamp = ApiCall.get(Endpoint.getCoursesForBootcamp(bootcampResponse.getData().getId()));

    CoursesForBootcampResponse courseResponse = getCoursesForBootcamp.as(CoursesForBootcampResponse.class);
    assertEquals(HttpStatus.SC_OK, getCoursesForBootcamp.statusCode());
    assertTrue(courseResponse.success, "Success field was expected to have value TRUE");
  }
}
