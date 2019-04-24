package model;

import model.generator.RsaEncryptionKeyGenerator;

import java.awt.*;
import java.math.BigInteger;

public class RsaEncryption {
    private final RsaEncryptionKeyGenerator encryptionKeyGenerator;
    private final BigInteger publicKey;
    private final BigInteger privateKey;
    private final BigInteger n;
    private String message;
    private String[] asciiBlocks;
    private String[] encryptedBlocks;
    private String[] decryptedBlocks;
    private String encryptedMessage;
    private String decryptedMessage;

    public RsaEncryption() {
        encryptionKeyGenerator = new RsaEncryptionKeyGenerator();
        publicKey = encryptionKeyGenerator.getPublicKey();
        privateKey = encryptionKeyGenerator.getPrivateKey();
        n = encryptionKeyGenerator.getN();
    }

    // encrypts provided message, persists encrypted blocks array and encrypted message
    public String encryptMessage() {
        String[] blocks = new String[255];
        int numberOfDigits = Integer.valueOf(n.intValue()).toString().length();
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
        asciiBlocks = new String[numberOfBlocks];
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

    // decrypts encrypted message, persists decrypted blocks array and decrypted message
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

    // Called by encrypt message
    // Encrypts individual ascii block
    private String encryptBlock(String asciiBlock) {
        String encryptedBlock = "";
        String[] delimitedSubBlocks = asciiBlock.split(",");
        for(int i = 0; i < delimitedSubBlocks.length; i++) {
//            pow(publicKey.intValue())).mod(new BigInteger(Integer.toString(n.intValue())))).toString() + ",";
            encryptedBlock += (new BigInteger(delimitedSubBlocks[i]).modPow(publicKey, n).toString() + ",");
        }
        return encryptedBlock;
    }

    // Called by decrypt message
    // Decrypts individual encrypted blocks to their ascii form
    private String decryptBlock(String encryptedBlock) {
        String decryptedBlock = "";
        String[] delimitedSubBlocks = encryptedBlock.split(",");
        for (int i = 0; i < delimitedSubBlocks.length; i++) {
            // pow(privateKey.intValue()).mod(new BigInteger(Integer.toString(n.intValue()))) + ","
            decryptedBlock += new BigInteger(delimitedSubBlocks[i]).modPow(privateKey, n).toString() + ",";
        }
        return decryptedBlock;
    }

    // Called by encrypt message
    // Converts individual message blocks to ascii
    private String convertToAscii(String block) {
        String ascii = "";
        for (int i = 0; i < block.length(); i++) {
            ascii += (((int)block.charAt(i))) + ",";
        }
        return ascii;
    }

    // Called by decrypt message
    // Converts individual decrypted ascii code blocks to english
    private String convertFromAscii(String asciiBlock) {
        String message = "";
        String[] delimitedSubBlocks = asciiBlock.split(",");
        for (int i = 0; i < delimitedSubBlocks.length; i++) {
            message += (char)Integer.parseInt(delimitedSubBlocks[i]);
        }
        return message;
    }

    // Allow controller to set message to be encrypted
    public void setMessage(String message) {
        this.message = message;
    }
}
