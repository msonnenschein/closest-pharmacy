package org.msonnenschein.closest.pharmacy.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.msonnenschein.closest.pharmacy.logic.ClosestPharmacyHandler;
import org.msonnenschein.closest.pharmacy.logic.PharmacyRepository;
import org.msonnenschein.closest.pharmacy.model.Address;
import org.msonnenschein.closest.pharmacy.model.PharmacyLocationResult;
import org.msonnenschein.closest.pharmacy.model.Point;

/** Tests {@link ClosestPharmacyController} */
@ExtendWith(MockitoExtension.class)
public class ClosestPharmacyControllerTest {

  @Mock private ClosestPharmacyHandler closestPharmacyHandler;

  @Test
  public void testGetClosestPharmacy() {
    double latitude = 3.4;
    double longitude = 1.5;
    PharmacyLocationResult expectedResult =
        new PharmacyLocationResult(
            "morgan", new Address("1234 Main", "Kansas City", "MO", "54321"), 78.9);
    when(closestPharmacyHandler.getClosestPharmacyLocation(
            new Point(latitude, longitude),
            PharmacyRepository.getCsvPharmacyLocations("/pharmacies.csv")))
        .thenReturn(expectedResult);
    ClosestPharmacyController closestPharmacyController =
        new ClosestPharmacyController(closestPharmacyHandler);
    PharmacyLocationResult actualResult =
        closestPharmacyController.getClosestPharmacy(latitude, longitude);
    assertEquals(expectedResult, actualResult);
  }
}
