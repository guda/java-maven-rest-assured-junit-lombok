package model.bootcamps;

import java.util.ArrayList;
import lombok.Data;

@Data
public class BootcampsResponse {
  public Boolean success;
  public Integer count;
  public Object pagination;
  public ArrayList<BootcampDataAllResponse> data;

}
