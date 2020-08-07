package org.msonnenschein.closest.pharmacy.logic;

import com.google.common.base.Verify;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.msonnenschein.closest.pharmacy.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Handles the logic of retrieving pharmacy location data */
@Component
public class ClosestPharmacyHandler {

  private static final DistanceCalculator DISTANCE_CALCULATOR = new HaversineDistanceCalculator();
  private final CoordinateHandler coordinateHandler;

  @Autowired
  public ClosestPharmacyHandler(CoordinateHandler coordinateHandler) {
    this.coordinateHandler = coordinateHandler;
  }

  /**
   * Processes the closest {@link PharmacyLocationResult} given a user location and pharmacy
   * locations
   *
   * @param userLocation the user's location as a {@link Point}. Cannot be null
   * @param csvPharmacyLocations the available {@link CsvPharmacyLocation} objects to get the
   *     closest distance from. Cannot be null or empty.
   * @return The closest {@link PharmacyLocationResult} for the given userLocation
   */
  public PharmacyLocationResult getClosestPharmacyLocation(
      Point userLocation, Collection<CsvPharmacyLocation> csvPharmacyLocations) {
    Verify.verifyNotNull(userLocation);
    Verify.verifyNotNull(csvPharmacyLocations);
    Verify.verify(!csvPharmacyLocations.isEmpty());

    Collection<Point> pharmacyPoints =
        csvPharmacyLocations.stream().map(this::extractPoint).collect(Collectors.toList());

    Map<Point, CsvPharmacyLocation> pointToPharmacyLocation =
        csvPharmacyLocations.stream()
            .collect(Collectors.toMap(this::extractPoint, Function.identity()));

    PharmacyPointDistance closestPharmacyDistance =
        coordinateHandler.determineClosestPharmacyPoint(
            userLocation, pharmacyPoints, DISTANCE_CALCULATOR);

    return buildPharmacyLocationResult(
        pointToPharmacyLocation.get(closestPharmacyDistance.getPharmacyPoint()),
        closestPharmacyDistance);
  }

  private PharmacyLocationResult buildPharmacyLocationResult(
      CsvPharmacyLocation csvPharmacyLocation, PharmacyPointDistance pharmacyPointDistance) {
    Address address =
        new Address(
            csvPharmacyLocation.getAddress(),
            csvPharmacyLocation.getCity(),
            csvPharmacyLocation.getState(),
            csvPharmacyLocation.getZip());
    return new PharmacyLocationResult(
        csvPharmacyLocation.getName().trim(),
        address,
        pharmacyPointDistance.getUserToPharmacyInMiles());
  }

  private Point extractPoint(CsvPharmacyLocation csvPharmacyLocation) {
    return new Point(csvPharmacyLocation.getLatitude(), csvPharmacyLocation.getLongitude());
  }
}
