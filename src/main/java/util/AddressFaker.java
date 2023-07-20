package util;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;
import model.fake.FakeAddress;

public class AddressFaker {

  public static FakeAddress getRandomAddress() {
    List<FakeAddress> addressList;
    try {
      addressList = new CsvToBeanBuilder(
          new FileReader("src/test/resources/data/real-addresses.csv"))
          .withType(FakeAddress.class)
          .withSeparator(';')
          .build()
          .parse();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    return addressList.get(new Random().nextInt(addressList.size()));
  }

  public static String setRandomAddress() {
    FakeAddress fakeAddress = getRandomAddress();
    return new StringJoiner(" ")
        .add(fakeAddress.getStreetNumber())
        .add(fakeAddress.getStreet())
        .add(fakeAddress.getTown())
        .add(fakeAddress.getCountry())
        .add(fakeAddress.getZip()).toString();
  }
}
