package org.jchain.blockchain;

import org.jchain.util.StringUtil;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class TransactionOutput {
    public String id;
    public PublicKey recipient; // also known as the new owner of these coins.
    public float value; // the amount of coins they own
    public String parentTransactionId; // the id of the transaction this output was created in

    public TransactionOutput(PublicKey recipient, float value, String parentTransactionId) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.recipient = recipient;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = StringUtil.applySha256(
                StringUtil.getStringFromKey(recipient) +
                        Float.toString(value) +
                        parentTransactionId
        );
    }

    public boolean isMine(PublicKey publicKey) {
        return (publicKey.equals(recipient));
    }
}
