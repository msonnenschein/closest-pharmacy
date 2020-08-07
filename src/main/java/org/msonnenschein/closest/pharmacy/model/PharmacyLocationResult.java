package org.msonnenschein.closest.pharmacy.model;

import lombok.Data;

/** Data class representing information about the nearest pharmacy */
@Data
public class PharmacyLocationResult {
  private final String name;
  private final Address address;
  private final double distance;
}
