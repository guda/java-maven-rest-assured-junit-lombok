package model.bootcamps;

import java.util.ArrayList;
import lombok.Data;

@Data
public class LocationData {

  public String type;
  public ArrayList<Double> coordinates;
  public String formattedAddress;
  public String street;
  public String city;
  public String state;
  public String zipcode;
  public String country;
}
