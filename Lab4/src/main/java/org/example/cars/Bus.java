package org.example.cars;

import org.example.people.Person;

public class Bus extends Automobile<Person> {
    public Bus(int numberOfPlaces) {
        super(numberOfPlaces);
    }
}
