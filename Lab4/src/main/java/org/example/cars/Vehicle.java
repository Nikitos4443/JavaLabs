package org.example.cars;

import org.example.cars.exceptions.PassengerAlreadyInVehicleException;
import org.example.cars.exceptions.PassengerNotFoundException;
import org.example.cars.exceptions.VehicleFullException;
import org.example.people.Person;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle<T extends Person> {

    private final int numberOfPlaces;
    private final List<T> seatPassengers;

    public Vehicle(int numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
        seatPassengers = new ArrayList<>();
    }

    public void seatPassenger(T person) {
        if(person.isInRoad()) {
            if(seatPassengers.contains(person)) {
                throw new PassengerAlreadyInVehicleException("This passenger is already in this vehicle");
            } else {
                throw new PassengerAlreadyInVehicleException("Passenger is already in the other vehicle");
            }
        }

        if(numberOfPlaces == seatPassengers.size()) {
            throw new VehicleFullException("There are no more seats in this vehicle");
        }

        person.setInRoad(true);
        seatPassengers.add(person);
    }

    public void dropPassenger(T person) {
        if(!seatPassengers.contains(person)) {
            throw new PassengerNotFoundException("Passenger not found in this vehicle");
        }

        person.setInRoad(false);
        seatPassengers.remove(person);
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public int getNumberOfFreePlaces() {
        return numberOfPlaces - seatPassengers.size();
    }

    public int getNumberOfSeatPlaces() {
        return seatPassengers.size();
    }
}
