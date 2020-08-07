package org.msonnenschein.closest.pharmacy.server;

import java.util.Collection;
import org.msonnenschein.closest.pharmacy.logic.ClosestPharmacyHandler;
import org.msonnenschein.closest.pharmacy.logic.PharmacyRepository;
import org.msonnenschein.closest.pharmacy.model.CsvPharmacyLocation;
import org.msonnenschein.closest.pharmacy.model.PharmacyLocationResult;
import org.msonnenschein.closest.pharmacy.model.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/** Controller for handling requests to the Closest Pharmacy application */
@RestController
public class ClosestPharmacyController {

  private static final Collection<CsvPharmacyLocation> CSV_PHARMACY_LOCATIONS =
      PharmacyRepository.getCsvPharmacyLocations("/pharmacies.csv");

  @Autowired private final ClosestPharmacyHandler closestPharmacyHandler;

  public ClosestPharmacyController(ClosestPharmacyHandler closestPharmacyHandler) {
    this.closestPharmacyHandler = closestPharmacyHandler;
  }

  /**
   * REST handler that determines the closest pharmacy location given a latitude and longitude
   *
   * @param latitude the latitude of the user
   * @param longitude the longitude of the user
   * @return The {@link PharmacyLocationResult} determined to be the closest to the user
   */
  @GetMapping(
      value = "/latitude/{latitude}/longitude/{longitude}/closest-pharmacy",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public PharmacyLocationResult getClosestPharmacy(
      @PathVariable double latitude, @PathVariable double longitude) {
    return closestPharmacyHandler.getClosestPharmacyLocation(
        new Point(latitude, longitude), CSV_PHARMACY_LOCATIONS);
  }
}
