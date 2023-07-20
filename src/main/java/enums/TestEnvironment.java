package enums;

public enum TestEnvironment {
  BASE_URL {
    @Override
    public String getUrl() {
      if (System.getProperty("eut") != null) {
        switch (System.getProperty("eut")) {
          case LOCAL:
            return "http://localhost:5000/api/v1/";
          case DEVELOP:
            return "http://localhost:5000/api/v1/";
          default:
            return "http://localhost:5000/api/v1/";
        }
      } else {
        return "http://localhost:5000/api/v1/";
      }
    }
  };

  public static final String LOCAL = "local";
  public static final String DEVELOP = "dev";

  public String getUrl() {
    return "";
  }
}
