package util;

import static io.restassured.RestAssured.given;

public class GenerateReport {

  public static void main(String[] arg) {
    //This url is where the Allure container is deployed. We are using localhost as example
    final String ALLURE_SERVER = "http://localhost:5050";
    //Project ID according to existent projects in your Allure container - Check endpoint for project creation >> `[POST]/projects`
    final String PROJECT_ID = "default";
    final String EXECUTION_NAME = "Allure%20Docker%20Service%20UI";
    final String endpoint =
        ALLURE_SERVER
            + "/allure-docker-service/generate-report?project_id="
            + PROJECT_ID
            + "&execution_name="
            + EXECUTION_NAME;

    given().get(endpoint);
  }
}
