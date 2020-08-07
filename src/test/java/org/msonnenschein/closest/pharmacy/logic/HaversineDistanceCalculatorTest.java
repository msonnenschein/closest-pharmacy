package org.msonnenschein.closest.pharmacy.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.common.base.VerifyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.msonnenschein.closest.pharmacy.model.DistanceCalculator;
import org.msonnenschein.closest.pharmacy.model.Point;

/** Tests {@link HaversineDistanceCalculator} */
public class HaversineDistanceCalculatorTest {

  private static final Point UNION_STATION = new Point(39.0853282, -94.5855784);
  private static final Point CVS_LEAWOOD = new Point(38.88323000, -94.64518000);
  private static final Point SAMS_TOPEKA = new Point(39.04160300, -95.76462600);

  // expected distances calculated using https://www.movable-type.co.uk/scripts/latlong.html
  private static final double UNION_STATION_TO_CVS_LEAWOOD_DISTANCE = 14.32;
  private static final double UNION_STATION_TO_SAMS_TOPEKA_DISTANCE = 63.31;

  private DistanceCalculator distanceCalculator;

  @BeforeEach
  public void setup() {
    distanceCalculator = new HaversineDistanceCalculator();
  }

  @Test
  public void testCalculateDistanceInMiles_NullPointOne() {
    assertThrows(
        VerifyException.class,
        () -> distanceCalculator.calculateDistanceInMiles(null, CVS_LEAWOOD));
  }

  @Test
  public void testCalculateDistanceInMiles_NullPointTwo() {
    assertThrows(
        VerifyException.class,
        () -> distanceCalculator.calculateDistanceInMiles(UNION_STATION, null));
  }

  @Test
  public void testCalculateDistanceInMiles() {
    assertEquals(
        UNION_STATION_TO_CVS_LEAWOOD_DISTANCE,
        distanceCalculator.calculateDistanceInMiles(UNION_STATION, CVS_LEAWOOD),
        0.1);
    assertEquals(
        UNION_STATION_TO_SAMS_TOPEKA_DISTANCE,
        distanceCalculator.calculateDistanceInMiles(UNION_STATION, SAMS_TOPEKA),
        0.1);
  }
}
