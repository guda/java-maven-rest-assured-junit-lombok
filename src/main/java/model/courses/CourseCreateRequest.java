package model.courses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.javafaker.Faker;
import constants.MinimumSkill;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class CourseCreateRequest {

  public String title;
  public String description;
  public String weeks;
  public Integer tuition;
  public String minimumSkill;
  public Boolean scholarhipsAvailable;

  public static CourseCreateRequest getRandomCourse() {
    Faker faker = new Faker();

    return CourseCreateRequest.builder()
        .title(faker.job().title())
        .description(faker.lorem().paragraph())
        .weeks(faker.number().digits(2))
        .tuition(faker.number().numberBetween(2000, 10000))
        .minimumSkill(MinimumSkill.INTERMEDIATE)
        .scholarhipsAvailable(faker.bool().bool())
        .build();
  }
}
