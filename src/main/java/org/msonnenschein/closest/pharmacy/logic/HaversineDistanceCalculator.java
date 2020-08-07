package org.msonnenschein.closest.pharmacy.logic;

import com.google.common.base.Verify;
import org.msonnenschein.closest.pharmacy.model.DistanceCalculator;
import org.msonnenschein.closest.pharmacy.model.Point;

/**
 * Uses the Haversine formula (https://en.wikipedia.org/wiki/Haversine_formula) to determine the
 * distance between two points on a sphere
 */
public class HaversineDistanceCalculator implements DistanceCalculator {

  private static final double RADIUS_OF_EARTH_IN_MILES = 3958.8;

  @Override
  public double calculateDistanceInMiles(Point pointOne, Point pointTwo) {
    Verify.verifyNotNull(pointOne);
    Verify.verifyNotNull(pointTwo);

    double latitudeOneInRadians = Math.toRadians(pointOne.getLatitude());
    double longitudeOneInRadians = Math.toRadians(pointOne.getLongitude());
    double latitudeTwoInRadians = Math.toRadians(pointTwo.getLatitude());
    double longitudeTwoInRadians = Math.toRadians(pointTwo.getLongitude());

    double rootOperand =
        haversine(latitudeTwoInRadians - latitudeOneInRadians)
            + Math.cos(latitudeOneInRadians)
                * Math.cos(latitudeTwoInRadians)
                * haversine(longitudeTwoInRadians - longitudeOneInRadians);

    // convert to to polar coordinates from rectangular coordinates
    double pointAngle = Math.atan2(Math.sqrt(rootOperand), Math.sqrt(1 - rootOperand));
    return 2 * RADIUS_OF_EARTH_IN_MILES * pointAngle;
  }

  private double haversine(double distanceInRadians) {
    return Math.pow(Math.sin(distanceInRadians / 2), 2);
  }
}
