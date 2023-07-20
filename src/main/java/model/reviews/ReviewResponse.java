package model.reviews;

import lombok.Data;

@Data
public class ReviewResponse {

  public Boolean success;
  public ReviewDataResponse data;
}
