package model.bootcamps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BootcampUpdateResponse {

  public Boolean success;
  public BootcampUpdateDataResponse data;
}
