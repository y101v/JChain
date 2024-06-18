package org.jchain.blockchain;

import com.google.gson.GsonBuilder;
import org.jchain.event.BlockEventListener;
import org.jchain.event.BlockLogger;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.stream.IntStream;
public class Main {
    public static ArrayList<Block> blockchain = new ArrayList<>();
    public static int difficulty = 6;

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        BlockEventListener eventListener = new BlockEventListener();
        eventListener.registerObserver(new BlockLogger());

        // Add blocks to the blockchain
        blockchain.add(new Block("Hi, I'm the first block", "0", eventListener));
        System.out.println("Trying to Mine block 1...");
        blockchain.get(0).mineBlock(difficulty);

        blockchain.add(new Block("Yo, I'm the second block", blockchain.get(blockchain.size() - 1).hash, eventListener));
        System.out.println("Trying to Mine block 2...");
        blockchain.get(1).mineBlock(difficulty);

        blockchain.add(new Block("Hey, I'm the third block", blockchain.get(blockchain.size() - 1).hash, eventListener));
        System.out.println("Trying to Mine block 3...");
        blockchain.get(2).mineBlock(difficulty);

        System.out.println("\nBlockchain is Valid: " + isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);
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