package org.msonnenschein.closest.pharmacy.model;

import lombok.Data;

/** Data class representing a latitude and longitude combination (on Earth) */
@Data
public class Point {
  private final double latitude;
  private final double longitude;
}
