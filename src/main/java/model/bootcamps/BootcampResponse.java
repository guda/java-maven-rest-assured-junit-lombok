package model.bootcamps;

import lombok.Data;

@Data
public class BootcampResponse {

  public Boolean success;
  public BootcampDataResponse data;
}
