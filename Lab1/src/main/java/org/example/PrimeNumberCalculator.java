package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimeNumberCalculator {

    public Integer getMaximumOnesInBinarySystem(int n) {

        var primeNumbers = getPrimeNumbersToN(n);

        var maxNumberOfOnes = 0;
        var maxIndex = 0;

        for(int i = 0; i < primeNumbers.size(); i++) {
            var numberOfOnes = 0;
            var binary = convertToBinary(primeNumbers.get(i));

            for(int j = 0; j < binary.length(); j++) {
                if(binary.charAt(j) == '1') {
                    numberOfOnes++;
                }
            }

            if(numberOfOnes > maxNumberOfOnes) {
                maxIndex = i;
                maxNumberOfOnes = numberOfOnes;
            }
        }

        return primeNumbers.get(maxIndex);
    }

    private List<Integer> getPrimeNumbersToN(int n) {

        if(n < 2) {
            throw new IllegalArgumentException("There are no prime numbers before 2");
        }

        List<Integer> primeNumbers = new ArrayList<>();

        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        isPrime[1] = false;

        for (int i = 2; i * i <= n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        for (int i = 2; i <= n; i++) {
            if(isPrime[i]) {
                primeNumbers.add(i);
            }
        }

        return primeNumbers;
    }

    private String convertToBinary(Integer number) {
        String bin = "";
        while (number > 0) {
            bin = (number % 2) + bin;
            number /= 2;
        }
        return bin;
    }
}