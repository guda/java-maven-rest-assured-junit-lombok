package model.bootcamps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BootcampCreateResponse {

  public Boolean success;
  public BootcampDataResponse data;
}
