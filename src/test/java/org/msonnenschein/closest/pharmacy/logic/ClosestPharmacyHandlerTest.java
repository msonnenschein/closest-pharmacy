package org.msonnenschein.closest.pharmacy.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import com.google.common.base.VerifyException;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.msonnenschein.closest.pharmacy.model.*;

/** Tests {@link ClosestPharmacyHandler} */
@ExtendWith(MockitoExtension.class)
public class ClosestPharmacyHandlerTest {

  private static final Point UNION_STATION = new Point(39.0853282, -94.5855784);

  @Mock private CoordinateHandler coordinateHandler;

  @Mock private DistanceCalculator distanceCalculator;

  private CsvPharmacyLocation closeLocation;
  private CsvPharmacyLocation farLocation;
  private ClosestPharmacyHandler closestPharmacyHandler;
  private Collection<CsvPharmacyLocation> csvPharmacyLocations;

  @BeforeEach
  public void setup() {

    closestPharmacyHandler = new ClosestPharmacyHandler(coordinateHandler);

    closeLocation = new CsvPharmacyLocation();
    closeLocation.setName("WALGREENS");
    closeLocation.setAddress("3696 SW TOPEKA BLVD");
    closeLocation.setCity("TOPEKA");
    closeLocation.setState("KS");
    closeLocation.setZip("66611");
    closeLocation.setLatitude(39.00142300);
    closeLocation.setLongitude(-95.68695000);

    farLocation = new CsvPharmacyLocation();
    farLocation.setName("North Pole");
    farLocation.setAddress("Ice");
    farLocation.setCity("Cap");
    farLocation.setState("Arctic");
    farLocation.setZip("Circle");
    farLocation.setLatitude(90.0);
    farLocation.setLongitude(135.0);

    csvPharmacyLocations = Set.of(farLocation, closeLocation);
  }

  @Test
  public void testGetClosestPharmacyLocation_TargetNull() {
    assertThrows(
        VerifyException.class,
        () ->
            closestPharmacyHandler.getClosestPharmacyLocation(
                null, Set.of(closeLocation, farLocation)));
  }

  @Test
  public void testGetClosestPharmacyLocation_LocationsNull() {
    assertThrows(
        VerifyException.class,
        () -> closestPharmacyHandler.getClosestPharmacyLocation(UNION_STATION, null));
  }

  @Test
  public void testGetClosestPharmacyLocation_LocationsEmpty() {
    assertThrows(
        VerifyException.class,
        () ->
            closestPharmacyHandler.getClosestPharmacyLocation(
                UNION_STATION, Collections.emptyList()));
  }

  @Test
  public void testGetClosestPharmacyLocation_ReturnsClosestPoint() {
    double distance = 5134.515;
    Point closeLocationPoint = new Point(closeLocation.getLatitude(), closeLocation.getLongitude());
    Point farLocationPoint = new Point(farLocation.getLatitude(), farLocation.getLongitude());
    Collection<Point> pharmacyPoints = Set.of(farLocationPoint, closeLocationPoint);
    when(coordinateHandler.determineClosestPharmacyPoint(
            eq(UNION_STATION), anyCollection(), isA(DistanceCalculator.class)))
        .thenReturn(new PharmacyPointDistance(closeLocationPoint, distance));
    Address expectedAddress =
        new Address(
            closeLocation.getAddress(),
            closeLocation.getCity(),
            closeLocation.getState(),
            closeLocation.getZip());
    PharmacyLocationResult expectedPharmacyLocationResult =
        new PharmacyLocationResult(closeLocation.getName(), expectedAddress, distance);
    PharmacyLocationResult actualPharmacyLocationResult =
        closestPharmacyHandler.getClosestPharmacyLocation(UNION_STATION, csvPharmacyLocations);
    assertEquals(expectedPharmacyLocationResult, actualPharmacyLocationResult);
  }
}
