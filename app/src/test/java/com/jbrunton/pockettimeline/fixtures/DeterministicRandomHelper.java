package com.jbrunton.pockettimeline.fixtures;

import com.jbrunton.pockettimeline.helpers.RandomHelper;

import java.util.Collection;
import java.util.Iterator;

public class DeterministicRandomHelper extends RandomHelper {
    private final Iterator<Integer> iterator;

    public DeterministicRandomHelper(Collection<Integer> sequence) {
        this.iterator = sequence.iterator();
    }

    @Override
    public int getNext(int max) {
        return iterator.next();
    }
}
