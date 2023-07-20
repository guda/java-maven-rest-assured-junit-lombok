package model.reviews;

import java.util.ArrayList;
import lombok.Data;

@Data
public class ReviewsResponse {

  public Boolean success;
  public Integer count;
  public Object pagination;
  public ArrayList<ReviewDataResponse> data;
}
