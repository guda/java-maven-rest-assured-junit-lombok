package model.auth;

import lombok.Data;

@Data
public class UpdateUserResponse {

  public Boolean success;
  public AuthDataResponse data;
}
