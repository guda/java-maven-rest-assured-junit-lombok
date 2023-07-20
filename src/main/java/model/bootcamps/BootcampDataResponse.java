package model.bootcamps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BootcampDataResponse {

  public LocationData location;
  public ArrayList<String> careers;
  public String photo;
  public boolean housing;
  public boolean jobAssistance;
  public boolean jobGuarantee;
  public boolean acceptGi;
  @JsonProperty("_id")
  public String id;
  public String name;
  public String description;
  public String website;
  public String phone;
  public String email;
  public String user;
  public String createdAt;
  public String slug;
  @JsonProperty("__v")
  public int v;
  @JsonProperty("id")
  public String duplicatedId;
  public String averageCost;
  public String averageRating;
}
