package model.auth;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResetPassRequest {

  public String password;

  public static AuthResetPassRequest resetPass() {
    Faker faker = new Faker();

    return AuthResetPassRequest.builder()
        .password(faker.internet().password(6, 10, true, false))
        .build();
  }
}
