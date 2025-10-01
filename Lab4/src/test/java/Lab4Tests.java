import org.junit.jupiter.api.Test;

import org.example.cars.*;
import org.example.cars.exceptions.*;
import org.example.people.*;
import org.example.road.Road;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class Lab4Tests {

    private Vehicle<Person> bus;
    private Vehicle<Firefighter> fireEngine;
    private Vehicle<Policeman> policeCar;
    private Road road;

    private DefaultPerson defaultPerson1;
    private DefaultPerson defaultPerson2;
    private Firefighter firefighter;
    private Policeman policeman;

    @BeforeEach
    public void setup() {
        bus = new Bus(2);
        fireEngine = new FireEngine(2);
        policeCar = new PoliceCar(1);

        road = new Road();

        defaultPerson1 = new DefaultPerson();
        defaultPerson2 = new DefaultPerson();
        firefighter = new Firefighter();
        policeman = new Policeman();
    }

    @Test
    public void testNumberOfPlaces() {
        assertEquals(2, bus.getNumberOfPlaces());
    }

    @Test
    public void testSeatPlacesAndFreePlaces() {
        bus.seatPassenger(defaultPerson1);
        bus.seatPassenger(defaultPerson2);
        assertEquals(2, bus.getNumberOfSeatPlaces());
        assertEquals(0, bus.getNumberOfFreePlaces());
        assertTrue(defaultPerson1.isInRoad());
        assertTrue(defaultPerson2.isInRoad());
    }

    @Test
    public void testSeatPassengerAlreadyInVehicleThrows() {
        bus.seatPassenger(defaultPerson1);

        try {
            bus.seatPassenger(defaultPerson1);
        } catch (PassengerAlreadyInVehicleException ex) {
            System.out.println(ex.getMessage());
            assertTrue(ex.getMessage().contains("already in this vehicle"));
        }
    }

    @Test
    public void testSeatPassengerAlreadyInOtherVehicleThrows() {
        bus.seatPassenger(defaultPerson1);
        Vehicle<Person> taxi = new Taxi(2);

        try {
            taxi.seatPassenger(defaultPerson1);
        } catch (PassengerAlreadyInVehicleException ex) {
            assertTrue(ex.getMessage().contains("already in the other vehicle"));
        }
    }

    @Test
    public void testSeatPassengerVehicleFullThrows() {
        bus.seatPassenger(new DefaultPerson());
        bus.seatPassenger(new DefaultPerson());

        try {
            bus.seatPassenger(new DefaultPerson());
        } catch (VehicleFullException ex) {
            assertTrue(ex.getMessage().contains("no more seats"));
        }
    }

    @Test
    public void testDropPassengerSuccessfully() {
        bus.seatPassenger(defaultPerson1);
        bus.dropPassenger(defaultPerson1);

        assertEquals(0, bus.getNumberOfSeatPlaces());
        assertFalse(defaultPerson1.isInRoad());
    }

    @Test
    public void testDropPassengerNotFoundThrows() {
        try {
            bus.dropPassenger(defaultPerson1);
        } catch (PassengerNotFoundException ex) {
            assertTrue(ex.getMessage().contains("not found"));
        }
    }

    @Test
    public void testAddCarToRoadSuccessfully() {
        road.addCarToRoad(bus);
        assertEquals(1, road.carsInRoad.size());
    }

    @Test
    public void testAddMultipleCarsToRoad() {
        road.addCarsToRoad(Arrays.asList(bus, fireEngine, policeCar));
        assertEquals(3, road.carsInRoad.size());
    }

    @Test
    public void testGetCountOfHumans() {
        bus.seatPassenger(new DefaultPerson());
        bus.seatPassenger(new DefaultPerson());

        fireEngine.seatPassenger(firefighter);
        fireEngine.seatPassenger(new Firefighter());

        policeCar.seatPassenger(policeman);

        road.addCarsToRoad(Arrays.asList(bus, fireEngine, policeCar));
        assertEquals(5, road.getCountOfHumans());
    }
}

