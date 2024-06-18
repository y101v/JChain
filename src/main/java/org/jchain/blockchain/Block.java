package org.jchain.blockchain;

import org.jchain.event.BlockEventListener;
import org.jchain.util.StringUtil;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


public class Block {

    public String hash;
    public String previousHash;
    private String data;
    private long timeStamp;
    private int nonce;
    private BlockEventListener eventListener;

    //Block Constructor.
    public Block(String data, String previousHash, BlockEventListener eventListener) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.eventListener = eventListener;
        this.hash = calculateHash();
    }

    //Calculate new hash based on blocks contents
    public String calculateHash() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        data
        );
    }

    public void mineBlock(int difficulty) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
        eventListener.notifyObservers(this);
    }
}
