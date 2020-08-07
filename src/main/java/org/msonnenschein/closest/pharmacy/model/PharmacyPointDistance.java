package org.msonnenschein.closest.pharmacy.model;

import lombok.Data;

/** Data class representing a point and its distance from a target */
@Data
public class PharmacyPointDistance {
  private final Point pharmacyPoint;
  private final double userToPharmacyInMiles;
}
