
import org.example.PrimeNumberCalculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PrimeNumberCalculatorTests {

    @Test
    public void TestIfPassBelowThan2() {
        PrimeNumberCalculator pnc = new PrimeNumberCalculator();
        assertThrows(IllegalArgumentException.class, () -> pnc.getMaximumOnesInBinarySystem(0));
    }

    @Test
    public void TestIfPassCorrectNumber() {
        PrimeNumberCalculator pnc = new PrimeNumberCalculator();

        var number =  pnc.getMaximumOnesInBinarySystem(10);
        assertEquals(7, number);

        var number2 =  pnc.getMaximumOnesInBinarySystem(100);
        assertEquals(31, number2);

        var number3 =  pnc.getMaximumOnesInBinarySystem(1000);
        assertEquals(991, number3);
    }
}
