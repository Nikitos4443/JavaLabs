package org.example.cars.exceptions;

public class PassengerAlreadyInVehicleException extends RuntimeException {
    public PassengerAlreadyInVehicleException(String message) {
        super(message);
    }
}
