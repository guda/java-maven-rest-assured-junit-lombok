package util;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.nio.file.Paths;

public class SendReportFiles {

  public static void main(String[] arg) {
    //This directory is where you have all your results locally, generally named as `allure-results`
    final String ALLURE_RESULTS_DIRECTORY = "target/allure-results";
    //This url is where the Allure container is deployed. We are using localhost as example
    final String ALLURE_SERVER = "http://localhost:5050";
    //Project ID according to existent projects in your Allure container - Check endpoint for project creation >> `[POST]/projects`
    final String PROJECT_ID = "default";
    final String endpoint = ALLURE_SERVER + "/allure-docker-service/send-results?project_id=" + PROJECT_ID;
    final String CURRENT_DIR = Paths.get("").toAbsolutePath().toString();
    final String RESULTS_DIRECTORY = CURRENT_DIR + "/" + ALLURE_RESULTS_DIRECTORY;
    File[] FILES_TO_SEND = new File(RESULTS_DIRECTORY).listFiles();

    RequestSpecification request = given().contentType(ContentType.MULTIPART);
    try {
      for (File file : FILES_TO_SEND) {
        request.multiPart("files[]", new File(file.getAbsolutePath()));
      }
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    request.post(endpoint);
  }
}
