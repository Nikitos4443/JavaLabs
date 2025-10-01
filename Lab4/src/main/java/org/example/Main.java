package org.example;

import org.example.cars.*;
import org.example.people.DefaultPerson;
import org.example.people.Firefighter;
import org.example.people.Person;
import org.example.people.Policeman;
import org.example.road.Road;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Road road = new Road();

        Vehicle<Person> bus = new Bus(18);
        Vehicle<Firefighter> fireEngine = new FireEngine(10);
        Vehicle<Policeman> policeCar = new PoliceCar(5);
        Vehicle<Person> taxi = new Taxi(6);

        DefaultPerson p1 = new DefaultPerson();
        DefaultPerson p2 = new DefaultPerson();
        DefaultPerson p3 = new DefaultPerson();
        DefaultPerson p4 = new DefaultPerson();

        Policeman pol1 = new Policeman();
        Policeman pol2 = new Policeman();
        Policeman pol3 = new Policeman();

        Firefighter f1 = new Firefighter();
        Firefighter f2 = new Firefighter();
        Firefighter f3 = new Firefighter();
        Firefighter f4 = new Firefighter();

        bus.seatPassenger(p1);
        bus.seatPassenger(p2);
        bus.seatPassenger(p3);
        bus.seatPassenger(p4);

        taxi.seatPassenger(new DefaultPerson());
        taxi.seatPassenger(new DefaultPerson());

        policeCar.seatPassenger(pol1);
        policeCar.seatPassenger(pol2);
        policeCar.seatPassenger(pol3);

        fireEngine.seatPassenger(f1);
        fireEngine.seatPassenger(f2);
        fireEngine.seatPassenger(f3);
        fireEngine.seatPassenger(f4);

        road.addCarsToRoad(Arrays.asList(bus,  fireEngine, policeCar, taxi));

        System.out.print(road.getCountOfHumans());
    }
}