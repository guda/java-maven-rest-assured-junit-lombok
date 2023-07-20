package model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUpdatePassRequest {

  public String currentPassword;
  public String newPassword;

  public static AuthUpdatePassRequest setPass(String currentPassword, String newPassword) {
    return AuthUpdatePassRequest.builder()
        .currentPassword(currentPassword)
        .newPassword(newPassword)
        .build();
  }
}
