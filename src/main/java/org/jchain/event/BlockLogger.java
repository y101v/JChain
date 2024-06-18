package org.jchain.event;

import org.jchain.blockchain.Block;

public class BlockLogger implements Observer{
    @Override
    public void update(Block block) {
        System.out.println("Logging Block: " + block.hash);
    }
}
