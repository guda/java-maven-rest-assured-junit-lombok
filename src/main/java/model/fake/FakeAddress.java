package model.fake;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class FakeAddress {

  @CsvBindByPosition(position = 0)
  private String streetNumber;
  @CsvBindByPosition(position = 1)
  private String street;
  @CsvBindByPosition(position = 2)
  private String town;
  @CsvBindByPosition(position = 3)
  private String country;
  @CsvBindByPosition(position = 4)
  private String zip;
}
