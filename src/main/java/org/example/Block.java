package org.example;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Block {
    public String hash;
    public String  previousHash;
    private String data;
    private long timestamp;


    public Block(String data, String previousHash) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.data = data;
        this.previousHash = previousHash;
        this.timestamp = new Date().getTime();
        this.hash = calculatedHash();
    }

    public String calculatedHash() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String calculatedHash = StringUtil.applySha256(previousHash + Long.toString(timestamp) + data);
        return calculatedHash;
    }

}
