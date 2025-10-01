package org.example.cars;

import org.example.people.Person;

public abstract class Automobile<T extends Person> extends Vehicle<T> {
    public Automobile(int numberOfPlaces) {
        super(numberOfPlaces);
    }
}
