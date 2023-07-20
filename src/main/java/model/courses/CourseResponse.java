package model.courses;

import lombok.Data;

@Data
public class CourseResponse {

  public Boolean success;
  public CoursesDataResponse data;
}
