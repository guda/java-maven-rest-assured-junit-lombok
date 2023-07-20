package model.courses;

import lombok.Data;

@Data
public class CourseCreateResponse {

  public Boolean success;
  public CoursesCreateDataResponse data;
}
