package org.jchain.blockchain;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
    public PrivateKey privateKey; // Acts as your digital signature
    public PublicKey publicKey; // Acts as your address

    // The amount of coin you own
    public HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();

    public Wallet() {
        generateKeyPair();
    }

    public void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstanceStrong();
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");

            // Initialize the key generator and generate a KeyPair
            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();

            // Set the public and private keys from the keyPair
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // Example methods in Wallet class
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

}