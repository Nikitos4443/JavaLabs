package org.example;

import java.util.Objects;

public class Person {
    private String firstName;
    private String lastName;
    private int age;

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;

        if (o == null) return false;

        if(!(o instanceof Person person)) return false;

        return
                Objects.equals(person.firstName, this.firstName) &&
                        Objects.equals(person.lastName, this.lastName) &&
                        person.age == this.age;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(firstName, lastName, age);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
