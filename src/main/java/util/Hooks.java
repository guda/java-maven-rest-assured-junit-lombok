package util;

import io.qameta.allure.Attachment;
import io.restassured.response.Response;
import java.io.StringWriter;
import java.util.StringJoiner;

public class Hooks {

  private static final ThreadLocal<String> requestLog = new ThreadLocal<String>();

  @Attachment("Request and response logs: ")
  public static byte[] attachLog() {
    return getRequestLog().getBytes();
  }

  public static String getRequestLog() {
    return requestLog.get();
  }

  public static void setRequestLog(StringWriter requestWriter, Response response) {
    requestLog.set(
        String.valueOf(new StringJoiner("\n")
            .add(getRequestLog())
            .add("REQUEST MADE:")
            .add(requestWriter.toString())
            .add("RESPONSE:")
            .add(response.asString() + "\n"))
    );
  }

  public static void clearLogs() {
    requestLog.set("");
  }
}
