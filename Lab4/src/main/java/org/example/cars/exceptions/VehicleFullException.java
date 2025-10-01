package org.example.cars.exceptions;

public class VehicleFullException extends RuntimeException {
    public VehicleFullException(String message) {
        super(message);
    }
}
