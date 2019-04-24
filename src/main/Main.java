package main;

import model.RsaEncryption;
import viewcontroller.Controller;

public class Main {
    // instantiates new controller
    public static void main (String[] args) {
        new Controller(new RsaEncryption());
    }
}
