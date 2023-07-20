package model.reviews;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import model.bootcamps.BootcampData;

@Data
public class ReviewDataResponse {

  @JsonProperty("_id")
  public String id;
  public String title;
  public String text;
  public Integer rating;
  public BootcampData bootcamp;
  public String user;
  public String createdAt;
  @JsonProperty("__v")
  public Integer v;

}
