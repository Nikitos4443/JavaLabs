package org.example;


public class Main {

    static Integer testNumber = 1;

    public static void main(String[] args) {
        PrimeNumberCalculator pnc = new PrimeNumberCalculator();
        try{
            pnc.getMaximumOnesInBinarySystem(0);
            assertEquals(1, 2);
        }catch(Exception e){
            if(e instanceof IllegalArgumentException){
                assertEquals(1, 1);
            }
        }

        var number =  pnc.getMaximumOnesInBinarySystem(10);
        assertEquals(7, number);

        var number2 =  pnc.getMaximumOnesInBinarySystem(100);
        assertEquals(31, number2);

        var number3 =  pnc.getMaximumOnesInBinarySystem(1000);
        assertEquals(991, number3);
    }

    private static void assertEquals(int expected, int actual) {
        if(expected != actual) {
            System.out.println("\u001B[31mTest " + testNumber + " not passed\u001B[0m"); // червоний
        } else {
            System.out.println("\u001B[32mTest " + testNumber + " passed\u001B[0m"); // зелений
        }
        testNumber++;
    }
}