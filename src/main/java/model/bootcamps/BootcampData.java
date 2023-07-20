package model.bootcamps;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BootcampData {

  @JsonProperty("_id")
  public String duplicateId;
  public String name;
  public String description;
  public String id;
}
