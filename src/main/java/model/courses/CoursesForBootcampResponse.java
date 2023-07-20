package model.courses;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursesForBootcampResponse {

  public Boolean success;
  public Integer count;
  public Object pagination;
  public ArrayList<CoursesCreateDataResponse> data;
}
