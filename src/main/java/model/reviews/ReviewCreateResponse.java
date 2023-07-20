package model.reviews;

import lombok.Data;

@Data
public class ReviewCreateResponse {

  public Boolean success;
  public ReviewCreateDataResponse data;
}
