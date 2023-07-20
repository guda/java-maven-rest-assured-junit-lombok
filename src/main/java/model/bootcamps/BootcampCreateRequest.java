package model.bootcamps;

import static util.AddressFaker.setRandomAddress;

import com.github.javafaker.Faker;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BootcampCreateRequest {

  public String name;
  public String description;
  public String website;
  public String phone;
  public String email;
  public String address;
  public List<String> careers;
  public Boolean housing;
  public Boolean jobAssistance;
  public Boolean jobGuarantee;
  public Boolean acceptGi;

  public static BootcampCreateRequest buildRandomBootcamp() {
    Faker faker = new Faker();

    return BootcampCreateRequest.builder()
        .name(faker.company().name())
        .description(faker.lorem().paragraph())
        .website("https://moderntech.com")
        .phone("(222) 222-2222")
        .email(faker.internet().emailAddress())
        .address(setRandomAddress())
        .careers(Arrays.asList("Web Development", "UI/UX", "Mobile Development"))
        .housing(faker.bool().bool())
        .jobAssistance(faker.bool().bool())
        .jobGuarantee(faker.bool().bool())
        .acceptGi(faker.bool().bool())
        .build();
  }
}
