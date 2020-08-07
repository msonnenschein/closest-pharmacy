package org.msonnenschein.closest.pharmacy.model;

import lombok.Data;

/** Data class representing an address */
@Data
public class Address {
  private final String street;
  private final String city;
  private final String state;
  private final String zip;
}
