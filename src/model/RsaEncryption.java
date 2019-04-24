package model;

import model.generator.RsaEncryptionKeyGenerator;

import java.awt.*;
import java.math.BigInteger;

public class RsaEncryption {
    private final RsaEncryptionKeyGenerator encryptionKeyGenerator;
    private final Point publicKey;
    private final Point privateKey;
    private String message;
    private String[] encryptedBlocks;
    private String[] decryptedBlocks;
    private String encryptedMessage;
    private String decryptedMessage;
    public RsaEncryption() {
        encryptionKeyGenerator = new RsaEncryptionKeyGenerator();
        publicKey = encryptionKeyGenerator.getPublicKey();
        privateKey = encryptionKeyGenerator.getPrivateKey();
    }

    public String decryptMessage() {
        decryptedBlocks = new String[encryptedBlocks.length];
        for (int i = 0; i < decryptedBlocks.length; i++) {
            decryptedBlocks[i] = decryptBlock(encryptedBlocks[i]);
        }
        decryptedMessage = "";
        for (int i = 0; i < decryptedBlocks.length; i++) {
            decryptedMessage += convertFromAscii(decryptedBlocks[i]);
        }
        return decryptedMessage;
    }

    public String encryptMessage() {
        String[] blocks = new String[255];
        int numberOfDigits = new Integer(publicKey.x).toString().length();
        int blockSize = message.length() / numberOfDigits;
        int j = 0;
        for (int i = 0; i <= numberOfDigits; i++) {
            if (j+blockSize > message.length()-1) {
                blocks[i] = message.substring(j);
            } else {
                blocks[i] = message.substring(j, j+blockSize);
                j+=blockSize;
            }
        }
        int numberOfBlocks = 0;
        while(blocks[numberOfBlocks] != null) {
            numberOfBlocks++;
        }
        String[] asciiBlocks = new String[numberOfBlocks];
        for (int i = 0; i < asciiBlocks.length; i++) {
            asciiBlocks[i] = convertToAscii(blocks[i]);
        }

        String asciiMessage = "";
        for (int i = 0; i < asciiBlocks.length; i++) {
            asciiMessage += asciiBlocks[i];
        }
        System.out.println("ascii " + asciiMessage);

        encryptedBlocks = new String[numberOfBlocks];
        for (int i = 0; i < asciiBlocks.length; i++) {
            encryptedBlocks[i] = encryptBlock(asciiBlocks[i]);
        }
        encryptedMessage = "";
        for (int i = 0; i < encryptedBlocks.length; i++) {
            encryptedMessage += encryptedBlocks[i];
        }
        return encryptedMessage;
    }

    private String decryptBlock(String encryptedBlock) {
        String decryptedBlock = "";
        String[] delimitedSubBlocks = encryptedBlock.split(",");
        for (int i = 0; i < delimitedSubBlocks.length; i++) {
            decryptedBlock += ((new BigInteger(delimitedSubBlocks[i]).pow(privateKey.x)).mod(new BigInteger(Integer.toString(privateKey.x)))).toString() + ",";
        }
        return decryptedBlock;
    }

    private String encryptBlock(String asciiBlock) {
        String encryptedBlock = "";
        String[] delimitedSubBlocks = asciiBlock.split(",");
        for(int i = 0; i < delimitedSubBlocks.length; i++) {
            encryptedBlock += ((new BigInteger(delimitedSubBlocks[i]).pow(publicKey.y)).mod(new BigInteger(Integer.toString(publicKey.x)))).toString() + ",";
        }
        return encryptedBlock;
    }

    private String convertToAscii(String block) {
        String ascii = "";
        for (int i = 0; i < block.length(); i++) {
            ascii += (Integer.toString(((int)block.charAt(i)))) + ",";
        }
        return ascii;
    }

    private String convertFromAscii(String asciiBlock) {
        String message = "";
        String[] delimitedSubBlocks = asciiBlock.split(",");
        for (int i = 0; i < delimitedSubBlocks.length; i++) {
            message += (char)Integer.parseInt(delimitedSubBlocks[i]);
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
