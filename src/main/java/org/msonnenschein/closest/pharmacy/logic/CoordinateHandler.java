package org.msonnenschein.closest.pharmacy.logic;

import com.google.common.base.Verify;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.msonnenschein.closest.pharmacy.model.DistanceCalculator;
import org.msonnenschein.closest.pharmacy.model.PharmacyPointDistance;
import org.msonnenschein.closest.pharmacy.model.Point;
import org.springframework.stereotype.Component;

/** Class to help with retrieving coordinate information */
@Component
public class CoordinateHandler {

  /**
   * Given a target latitude/longitude {@link Point} and candidate latitude/longitude {@link Point}
   * objects, determines the closest point to the target
   *
   * @param targetPoint the target latitude/longitude to find the closest point to. Cannot be null.
   * @param pharmacyPoints the pharmacy {@link Point} objects that may be closest. Cannot be null or
   *     empty.
   * @param distanceCalculator the {@link DistanceCalculator} to use for determining distance.
   *     Cannot be null.
   * @return the {@link PharmacyPointDistance} representing the target location, the closest
   *     pharmacy, and the distance between them
   */
  public PharmacyPointDistance determineClosestPharmacyPoint(
      Point targetPoint, Collection<Point> pharmacyPoints, DistanceCalculator distanceCalculator) {
    Verify.verifyNotNull(targetPoint);
    Verify.verifyNotNull(pharmacyPoints);
    Verify.verify(!pharmacyPoints.isEmpty());
    Verify.verifyNotNull(distanceCalculator);
    Map<Point, Double> pointToDistanceFromTarget =
        pharmacyPoints.stream()
            .collect(
                Collectors.toMap(
                    Function.identity(),
                    candidatePoint ->
                        distanceCalculator.calculateDistanceInMiles(targetPoint, candidatePoint)));
    Optional<Map.Entry<Point, Double>> closestPointDistanceOptional =
        pointToDistanceFromTarget.entrySet().stream()
            .min(Comparator.comparingDouble(Map.Entry::getValue));
    Map.Entry<Point, Double> closestPointDistance = closestPointDistanceOptional.get();
    return new PharmacyPointDistance(
        closestPointDistance.getKey(), closestPointDistance.getValue());
  }
}
