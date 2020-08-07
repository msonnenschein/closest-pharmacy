package org.msonnenschein.closest.pharmacy.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.msonnenschein.closest.pharmacy.model.CsvPharmacyLocation;

/** Tests {@link PharmacyRepository} */
public class PharmacyRepositoryTest {

  @Test
  public void testGetCsvPharmacyLocations() {
    CsvPharmacyLocation expectedCsvPharmacyLocation = new CsvPharmacyLocation();
    expectedCsvPharmacyLocation.setName("WALGREENS");
    expectedCsvPharmacyLocation.setAddress("3696 SW TOPEKA BLVD");
    expectedCsvPharmacyLocation.setCity("TOPEKA");
    expectedCsvPharmacyLocation.setState("KS");
    expectedCsvPharmacyLocation.setZip("66611");
    expectedCsvPharmacyLocation.setLatitude(39.00142300);
    expectedCsvPharmacyLocation.setLongitude(-95.68695000);

    Assertions.assertTrue(
        PharmacyRepository.getCsvPharmacyLocations("/pharmacies.csv")
            .contains(expectedCsvPharmacyLocation));
  }
}
