package model.reviews;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ReviewCreateRequest {

  public String title;
  public String text;
  public Number rating;

  public static ReviewCreateRequest getRandomReview() {
    Faker faker = new Faker();

    return ReviewCreateRequest.builder()
        .title(faker.lorem().words(5).toString())
        .text(faker.harryPotter().spell())
        .rating(faker.number().numberBetween(1, 10))
        .build();
  }
}
