package main;

import model.RsaEncryption;
import model.generator.PrimeNumberGenerator;
import model.generator.RsaEncryptionKeyGenerator;
import viewcontroller.Controller;
import viewcontroller.View;

import java.awt.*;
import java.math.BigInteger;

import static model.generator.PrimeNumberGenerator.generatePrime;

public class Main {
    public static void main (String[] args) {
        Controller controller = new Controller(new RsaEncryption());
    }
}
