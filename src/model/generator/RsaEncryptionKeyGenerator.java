package model.generator;

import java.awt.*;
import java.math.BigInteger;

import static model.generator.PrimeNumberGenerator.generatePrime;

public class RsaEncryptionKeyGenerator {
    private final BigInteger ONE = new BigInteger("1");
    private final BigInteger ZERO = new BigInteger("0");
    private BigInteger p = generatePrime();
    private BigInteger q = generatePrime();
    private BigInteger n = p.multiply(q);  // modulus
    private BigInteger phi = (p.subtract(new BigInteger("1"))).multiply(q.subtract(new BigInteger("1")));
    private BigInteger e = generateE();    // public exponent or encryption exponent
    private BigInteger d = generateD();    // private exponent or decryption exponent

    public RsaEncryptionKeyGenerator() {
        System.out.println(p);
        System.out.println(q);
        System.out.println(phi);
        System.out.println(e);
        System.out.println(d);
    }

    // Generate the encryption exponent
    private BigInteger generateE() {
        BigInteger e = generatePrime();
        if (e.gcd(phi) != ONE && (e.compareTo(ONE) == -1 || e.compareTo(phi) == 1 || e.compareTo(ONE) == 0 || e.compareTo(phi) == 0)) {
            return null;
        }
        return e;
    }

    //Generate the decryption exponent
    private BigInteger generateD() {
        BigInteger d = e.modInverse(phi);
        if((((e.multiply(d)).subtract(ONE)).mod(phi)).equals(ZERO)) {
            return d;
        }
        return null;
    }

    //Getters for keys
    public BigInteger getPublicKey() { return e; }

    public BigInteger getPrivateKey() { return d; }

    public BigInteger getN() {return n;}
}
