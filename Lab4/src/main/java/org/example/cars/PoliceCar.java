package org.example.cars;

import org.example.people.Policeman;

public class PoliceCar extends Automobile<Policeman> {
    public PoliceCar(int numberOfPlaces) {
        super(numberOfPlaces);
    }
}
