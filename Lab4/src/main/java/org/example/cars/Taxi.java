package org.example.cars;

import org.example.people.Person;

public class Taxi extends Automobile<Person> {
    public Taxi(int numberOfPlaces) {
        super(numberOfPlaces);
    }
}
