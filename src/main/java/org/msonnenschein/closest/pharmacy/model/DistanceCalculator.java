package org.msonnenschein.closest.pharmacy.model;

/** Defines utilities for calculating distances between points */
public interface DistanceCalculator {

  /**
   * Given two {@link Point} objects, returns the distance in miles between the points
   *
   * @param pointOne the first {@link Point} to use. Cannot be null.
   * @param pointTwo the second {@link Point} to use. Cannot be null.
   * @return The distance between the two Points in miles
   */
  double calculateDistanceInMiles(Point pointOne, Point pointTwo);
}
