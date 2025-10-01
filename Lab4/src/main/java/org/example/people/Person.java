package org.example.people;

public abstract class Person {
    private boolean isInRoad;

    public Person() {
        this.isInRoad = false;
    }

    public void setInRoad(boolean isInRoad) {
        this.isInRoad = isInRoad;
    }

    public boolean isInRoad() {
        return isInRoad;
    }
}
