package constants;

public class Endpoint {

  public static final String AUTH_REGISTER = "auth/register";
  public static final String AUTH_LOGIN = "auth/login";
  public static final String AUTH_ME = "auth/me";
  public static final String AUTH_FORGOTPASS = "auth/forgotpassword";
  public static  final String AUTH_UPDATE_PASS="auth/updatepassword";
  public static final String AUTH_RESETPASSWORD = "auth/resetpassword";
  public static final String AUTH_LOGOUT = "auth/logout";
  public static final String AUTH_UPDATE_USER = "auth/updatedetails";
  public static final String BOOTCAMP = "bootcamps";
  public static final String REVIEW = "reviews";
  public static final String COURSE = "courses";


  public static String getBootcampById(String bootcampId) {
    return "bootcamps/" + bootcampId;
  }
  public static String addBootcampById(String bootcampId) {
    return "bootcamps/" + bootcampId;
  }
  public static String getReviewById(String reviewId) {
    return "reviews/" + reviewId;
  }
  public static String addReviewForBootcampById(String bootcampId) {
    return "bootcamps/" + bootcampId + "/reviews";
  }

  public static String getReviewsForBootcampById(String bootcampId) {
    return "bootcamps/" + bootcampId + "/reviews";
  }

  public static String deleteReview(String bootcampId) {
    return "reviews/" + bootcampId;
  }
  public static String getCourseById(String courseId) {
    return "courses/" + courseId;
  }
  public static String addCourseForBootcampById(String bootcampId) {
    return "bootcamps/" + bootcampId + "/courses";
  }
  public static String updateCourse(String courseId) {
    return "courses/" + courseId;
  }
  public static String getCoursesForBootcamp(String bootcampId){return "bootcamps/" + bootcampId + "/courses";}
  public static String resetPassword(String passwordToken) {
    return "auth/resetpassword/" + passwordToken;
  }
  public static String uploadPhoto(String bootcampId) {
    return "bootcamps/" + bootcampId + "/photo";
  }
}
