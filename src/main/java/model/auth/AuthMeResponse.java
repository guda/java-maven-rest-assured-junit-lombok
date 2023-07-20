package model.auth;

import lombok.Data;
import model.auth.AuthDataResponse;

@Data
public class AuthMeResponse {

  public Boolean success;
  public AuthDataResponse data;
}
