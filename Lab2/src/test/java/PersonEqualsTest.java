import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.example.Person;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PersonEqualsTest {

    @Test
    public void TestEqualsVerifier() {
        EqualsVerifier
                .forClass(Person.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void TestIfEqualIdenticalObjects() {
        Person person = new Person("Vanya", "Pupkin", 92);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        var json = gson.toJson(person);

        Person personToCompare = gson.fromJson(json, Person.class);

        assertTrue(person.equals(personToCompare));
    }

    @Test
    public void TestIfNotEqualDifferentObjects() {
        Person person = new Person("Vanya", "Pupkin", 92);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        var json = gson.toJson(person);

        Person personToCompare = gson.fromJson(json, Person.class);
        personToCompare.setAge(10);

        assertFalse(person.equals(personToCompare));
    }
}
