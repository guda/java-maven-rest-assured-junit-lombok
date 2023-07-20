package model.reviews;

import lombok.Data;

@Data
public class ReviewNegativeResponse {

  public Boolean success;
  public String error;
}
