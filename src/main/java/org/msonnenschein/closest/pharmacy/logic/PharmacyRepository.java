package org.msonnenschein.closest.pharmacy.logic;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import org.msonnenschein.closest.pharmacy.model.ClosestPharmacyException;
import org.msonnenschein.closest.pharmacy.model.CsvPharmacyLocation;

/** Singleton container of pharmacy data read from a .csv file */
public class PharmacyRepository {

  private static Collection<CsvPharmacyLocation> csvPharmacyLocations;

  private PharmacyRepository() {}

  /**
   * Reads {@link CsvPharmacyLocation} objects from the given filepath
   *
   * @param filepath the filepath to read from
   * @return the {@link CsvPharmacyLocation} objects read from the file
   */
  public static Collection<CsvPharmacyLocation> getCsvPharmacyLocations(String filepath) {
    if (csvPharmacyLocations == null) {
      csvPharmacyLocations = readCsvPharmacyLocations(filepath);
    }
    return csvPharmacyLocations;
  }

  private static Collection<CsvPharmacyLocation> readCsvPharmacyLocations(String filepath) {
    MappingStrategy<CsvPharmacyLocation> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
    mappingStrategy.setType(CsvPharmacyLocation.class);
    try {
      Reader reader = new InputStreamReader(PharmacyRepository.class.getResourceAsStream(filepath));
      CsvToBean<CsvPharmacyLocation> pharmacyLocationCsvToBean =
          new CsvToBeanBuilder<CsvPharmacyLocation>(reader)
              .withType(CsvPharmacyLocation.class)
              .withMappingStrategy(mappingStrategy)
              .build();
      return pharmacyLocationCsvToBean.parse();
    } catch (Exception e) {
      throw new ClosestPharmacyException("Exception occurred when reading pharmacy locations", e);
    }
  }
}
