package org.msonnenschein.closest.pharmacy.model;

/** Specific exception thrown when an error occurs in the Closest Pharmacy application */
public class ClosestPharmacyException extends RuntimeException {
  public ClosestPharmacyException(String message, Throwable cause) {
    super(message, cause);
  }
}
