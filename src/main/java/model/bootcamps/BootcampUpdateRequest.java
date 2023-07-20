package model.bootcamps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class BootcampUpdateRequest {

  public LocationData location;
  public ArrayList<String> careers;
  public String photo;
  public Boolean housing;
  public Boolean jobAssistance;
  public Boolean jobGuarantee;
  public Boolean acceptGi;
  @JsonProperty("_id")
  public String id;
  public String user;
  public String name;
  public String description;
  public String website;
  public String phone;
  public String email;
  public String createdAt;
  public String slug;
  @JsonProperty("__v")
  public Integer v;
  @JsonProperty("id")
  public String duplicatedId;
}
