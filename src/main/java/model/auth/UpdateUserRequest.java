package model.auth;

import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequest {

  public String name;
  public String email;

  public static UpdateUserRequest updateDetails() {
    Faker faker = new Faker();

    return builder()
        .name(faker.name().firstName() + faker.name().lastName())
        .email(faker.internet().emailAddress())
        .build();
  }
}
