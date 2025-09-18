package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static void main(String[] args) {
        Person person = new Person("Vanya", "Pupkin", 92);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        var json = gson.toJson(person);
        System.out.println(json);

        Person personToCompare = gson.fromJson(json, Person.class);

        System.out.println("Objects equal:  " + person.equals(personToCompare));
    }
}