package model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthForgotPassRequest {

  public String email;

  public static AuthForgotPassRequest setEmail(String email) {
    return AuthForgotPassRequest.builder()
        .email(email)
        .build();
  }
}
