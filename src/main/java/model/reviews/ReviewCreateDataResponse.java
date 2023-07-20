package model.reviews;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReviewCreateDataResponse {

  @JsonProperty("_id")
  public String id;
  public String title;
  public String text;
  public Integer rating;
  public String bootcamp;
  public String user;
  public String createdAt;
  @JsonProperty("__v")
  public Integer v;
}
