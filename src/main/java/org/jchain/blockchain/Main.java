package org.jchain.blockchain;

import com.google.gson.GsonBuilder;
import org.jchain.event.BlockEventListener;
import org.jchain.event.BlockLogger;
import org.jchain.util.StringUtil;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;
public class Main {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>(); //list of all unspent transactions.
    public static int difficulty = 5;
    public static Wallet walletA;
    public static Wallet walletB;

    public static void main(String[] args) {
        // Setup Bouncy Castle as a Security Provider
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        // Create the new wallets
        walletA = new Wallet();
        walletB = new Wallet();

        // Test public and private keys
        System.out.println("Private key of walletA:");
       // System.out.println(StringUtil.getStringFromKey(walletA.privateKey));

        System.out.println("Public key of walletA:");
        System.out.println(StringUtil.getStringFromKey(walletA.publicKey));

        // Create a test transaction from WalletA to walletB
        Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
        transaction.generateSignature(walletA.privateKey);

        // Verify the signature works and verify it from the public key
        System.out.println("Is signature verified:");
        System.out.println(transaction.verifiySignature());
    }

    public static boolean isChainValid() {
        return IntStream.range(1, blockchain.size())
                .parallel()  // Use parallel processing for large blockchains
                .allMatch(i -> {
                    Block currentBlock = blockchain.get(i);
                    Block previousBlock = blockchain.get(i - 1);
                    // Compare registered hash and calculated hash
                    try {
                        if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                            System.out.println("Current Hashes not equal");
                            return false;
                        }
                    } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                    // Compare previous hash and registered previous hash
                    if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                        System.out.println("Previous Hashes not equal");
                        return false;
                    }
                    return true;
                });
    }
}