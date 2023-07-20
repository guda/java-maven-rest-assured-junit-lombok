package model.courses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CoursesCreateDataResponse {

  public Boolean scholarshipAvailable;
  @JsonProperty("_id")
  public String id;
  public String title;
  public String description;
  public String weeks;
  public Integer tuition;
  public String minimumSkill;
  public String bootcamp;
  public String user;
  public String createdAt;
  @JsonProperty("__v")
  public Integer v;

}
