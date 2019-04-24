package model.generator;

import java.math.BigInteger;
import java.util.Random;

public abstract class PrimeNumberGenerator {
    // Generate and validate prime number
    // If a non-prime is generated, recursively generates until prime is generated
    public static BigInteger generatePrime() {
        return BigInteger.probablePrime(10, new Random());
    }
}
