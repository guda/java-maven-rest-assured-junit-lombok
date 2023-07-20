package model.reviews;

import java.util.ArrayList;
import lombok.Data;

@Data
public class ReviewsForBootcampResponse {

  public Boolean success;
  public Integer count;
  public ArrayList<ReviewCreateDataResponse> data;
}
