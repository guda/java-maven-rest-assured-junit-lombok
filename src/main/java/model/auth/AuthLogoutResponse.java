package model.auth;

import lombok.Data;

@Data
public class AuthLogoutResponse {

  public Boolean success;
  public AuthDataResponse data;
}
