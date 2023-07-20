package model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthDataResponse {

  public String role;
  @JsonProperty("_id")
  public String id;
  public String name;
  public String email;
  public String createdAt;
  @JsonProperty("__v")
  public Integer v;
}
