package org.msonnenschein.closest.pharmacy.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

/** Data class representing a row in the pharmacies.csv file */
@Data
public class CsvPharmacyLocation {
  @CsvBindByName private String name;

  @CsvBindByName private String address;

  @CsvBindByName private String city;

  @CsvBindByName private String state;

  @CsvBindByName private String zip;

  @CsvBindByName private double latitude;

  @CsvBindByName private double longitude;
}
