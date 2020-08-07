package org.msonnenschein.closest.pharmacy.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.google.common.base.VerifyException;
import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.msonnenschein.closest.pharmacy.model.DistanceCalculator;
import org.msonnenschein.closest.pharmacy.model.PharmacyPointDistance;
import org.msonnenschein.closest.pharmacy.model.Point;

/** Tests {@link CoordinateHandler} */
@ExtendWith(MockitoExtension.class)
public class CoordinateHandlerTest {

  private static final Point UNION_STATION = new Point(39.0853282, -94.5855784);
  private static final Point CVS_LEAWOOD = new Point(38.88323000, -94.64518000);
  private static final Point SAMS_TOPEKA = new Point(39.04160300, -95.76462600);
  private static final double UNION_STATION_TO_CVS_LEAWOOD_DISTANCE = 14.32;
  private static final double UNION_STATION_TO_SAMS_TOPEKA_DISTANCE = 63.31;

  @Mock private DistanceCalculator distanceCalculator;

  private CoordinateHandler coordinateHandler;

  @BeforeEach
  public void setup() {
    coordinateHandler = new CoordinateHandler();
  }

  @Test
  public void testDetermineClosestPharmacyPoint_TargetNull() {
    assertThrows(
        VerifyException.class,
        () ->
            coordinateHandler.determineClosestPharmacyPoint(
                null, Set.of(CVS_LEAWOOD, SAMS_TOPEKA), distanceCalculator));
  }

  @Test
  public void testDetermineClosestPharmacyPoint_PharmaciesNull() {
    assertThrows(
        VerifyException.class,
        () ->
            coordinateHandler.determineClosestPharmacyPoint(
                UNION_STATION, null, distanceCalculator));
  }

  @Test
  public void testDetermineClosestPharmacyPoint_PharmaciesEmpty() {
    assertThrows(
        VerifyException.class,
        () ->
            coordinateHandler.determineClosestPharmacyPoint(
                UNION_STATION, Collections.emptyList(), distanceCalculator));
  }

  @Test
  public void testDetermineClosestPharmacyPoint_DistanceCalculatorNull() {
    assertThrows(
        VerifyException.class,
        () ->
            coordinateHandler.determineClosestPharmacyPoint(
                UNION_STATION, Set.of(CVS_LEAWOOD), null));
  }

  @Test
  public void testDetermineClosestPharmacyPoint_ReturnsClosest() {
    when(distanceCalculator.calculateDistanceInMiles(UNION_STATION, CVS_LEAWOOD))
        .thenReturn(UNION_STATION_TO_CVS_LEAWOOD_DISTANCE);
    when(distanceCalculator.calculateDistanceInMiles(UNION_STATION, SAMS_TOPEKA))
        .thenReturn(UNION_STATION_TO_SAMS_TOPEKA_DISTANCE);
    PharmacyPointDistance expectedPharmacyPointDistance =
        new PharmacyPointDistance(CVS_LEAWOOD, UNION_STATION_TO_CVS_LEAWOOD_DISTANCE);
    PharmacyPointDistance actualPharmacyPointDistance =
        coordinateHandler.determineClosestPharmacyPoint(
            UNION_STATION, Set.of(CVS_LEAWOOD, SAMS_TOPEKA), distanceCalculator);
    assertEquals(expectedPharmacyPointDistance, actualPharmacyPointDistance);
  }
}
