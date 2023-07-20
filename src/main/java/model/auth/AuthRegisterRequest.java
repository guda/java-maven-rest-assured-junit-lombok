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
public class AuthRegisterRequest {

  public String name;
  public String email;
  public String password;
  public String role;

  public static AuthRegisterRequest getRandomUser(String role) {
    Faker faker = new Faker();

    return AuthRegisterRequest.builder()
        .name(faker.name().firstName() + " " + faker.name().lastName())
        .email(faker.internet().emailAddress())
        .password(faker.internet().password())
        .role(role)
        .build();
  }
}
