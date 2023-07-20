package util;

import static io.restassured.RestAssured.given;
import static util.Hooks.setRequestLog;

import io.qameta.allure.Step;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import java.io.PrintStream;
import java.io.StringWriter;
import org.apache.commons.io.output.WriterOutputStream;

public class ApiCall {

  /**
   * GET call made over RestAssured
   *
   * @param endpoint Endpoint path
   * @return Returns RestAssured library 'Response' type
   */
  @Step("GET method resource identified by the request URI ")
  public static Response get(String endpoint) {
    StringWriter requestWriter = new StringWriter();
    PrintStream requestCapture = new PrintStream(new WriterOutputStream(requestWriter, "UTF-8"), true);

    Response response = given()
        .filter(new RequestLoggingFilter(requestCapture))
        .get(endpoint);

    setRequestLog(requestWriter, response);

    return response;
  }

  /**
   * GET call made over RestAssured
   *
   * @param endpoint Endpoint path
   * @param header   Headers sent with this request
   * @return Returns RestAssured library 'Response' type
   */
  @Step("GET method resource identified by the request URI with headers")
  public static Response get(String endpoint, Header header) {
    StringWriter requestWriter = new StringWriter();
    PrintStream requestCapture = new PrintStream(new WriterOutputStream(requestWriter, "UTF-8"), true);

    Response response = given()
        .contentType(ContentType.JSON)
        .filter(new RequestLoggingFilter(requestCapture))
        .header(header)
        .get(endpoint);

    setRequestLog(requestWriter, response);

    return response;
  }

  /**
   * POST call made over RestAssured
   *
   * @param endpoint Endpoint path
   * @param header   Headers sent with this request
   * @param request  Payload sent with this request
   * @return Returns RestAssured library 'Response' type
   */
  @Step("POST method resource identified by the request URI ")
  public static Response post(String endpoint, Header header, Object request) {
    StringWriter requestWriter = new StringWriter();
    PrintStream requestCapture = new PrintStream(new WriterOutputStream(requestWriter, "UTF-8"), true);

    Response response = given()
        .contentType(ContentType.JSON)
        .filter(new RequestLoggingFilter(requestCapture))
        .header(header)
        .body(request)
        .post(endpoint);

    setRequestLog(requestWriter, response);

    return response;
  }

  /**
   * POST call made over RestAssured
   *
   * @param endpoint Endpoint path
   * @param request  Payload sent with this request
   * @return Returns RestAssured library 'Response' type
   */
  @Step("POST method resource identified by the request URI without header")
  public static Response post(String endpoint, Object request) {
    StringWriter requestWriter = new StringWriter();
    PrintStream requestCapture = new PrintStream(new WriterOutputStream(requestWriter, "UTF-8"), true);

    Response response = given()
        .contentType(ContentType.JSON)
        .filter(new RequestLoggingFilter(requestCapture))
        .body(request)
        .post(endpoint);

    setRequestLog(requestWriter, response);

    return response;
  }

  /**
   * PUT call made over RestAssured
   *
   * @param endpoint Endpoint path
   * @param header   Headers sent with this request
   * @param request  Payload sent with this request
   * @return Returns RestAssured library 'Response' type
   */
  @Step("PUT method resource identified by the request URI ")
  public static Response put(String endpoint, Header header, Object request) {
    StringWriter requestWriter = new StringWriter();
    PrintStream requestCapture = new PrintStream(new WriterOutputStream(requestWriter, "UTF-8"), true);

    Response response = given()
        .contentType(ContentType.JSON)
        .filter(new RequestLoggingFilter(requestCapture))
        .header(header)
        .body(request)
        .put(endpoint);

    setRequestLog(requestWriter, response);

    return response;
  }

  /**
   * PUT call made over RestAssured
   *
   * @param endpoint Endpoint path
   * @param request  Payload sent with this request
   * @return Returns RestAssured library 'Response' type
   */
  @Step("PUT method resource identified by the request URI without header")
  public static Response put(String endpoint, Object request) {
    StringWriter requestWriter = new StringWriter();
    PrintStream requestCapture = new PrintStream(new WriterOutputStream(requestWriter, "UTF-8"), true);

    Response response = given()
        .contentType(ContentType.JSON)
        .filter(new RequestLoggingFilter(requestCapture))
        .body(request)
        .put(endpoint);

    setRequestLog(requestWriter, response);

    return response;
  }

  /**
   * DELETE call made over RestAssured
   *
   * @param endpoint Endpoint path
   * @param header   Headers sent with this request
   * @return Returns RestAssured library 'Response' type
   */
  @Step("DELETE method resource identified by the request URI ")
  public static Response delete(String endpoint, Header header) {
    StringWriter requestWriter = new StringWriter();
    PrintStream requestCapture = new PrintStream(new WriterOutputStream(requestWriter, "UTF-8"), true);

    Response response = given()
        .contentType(ContentType.JSON)
        .filter(new RequestLoggingFilter(requestCapture))
        .header(header)
        .delete(endpoint);

    setRequestLog(requestWriter, response);

    return response;
  }
}
