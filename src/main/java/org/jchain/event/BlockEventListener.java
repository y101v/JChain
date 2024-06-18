package org.jchain.event;

import org.jchain.blockchain.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockEventListener {
        private List<Observer> observers = new ArrayList<>();

        public void registerObserver(Observer observer) {
            observers.add(observer);
        }

        public void removeObserver(Observer observer) {
            observers.remove(observer);
        }

        public void notifyObservers(Block block) {
            for (Observer observer : observers) {
                observer.update(block);
            }
        }
}
