package model.courses;

import java.util.ArrayList;
import lombok.Data;

@Data
public class CoursesResponse {

  public Boolean success;
  public Integer count;
  public Object pagination;
  public ArrayList<CoursesDataResponse> data;
}
