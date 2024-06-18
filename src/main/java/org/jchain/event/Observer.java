package org.jchain.event;

import org.jchain.blockchain.Block;

public interface Observer {
    void update(Block block);
}
