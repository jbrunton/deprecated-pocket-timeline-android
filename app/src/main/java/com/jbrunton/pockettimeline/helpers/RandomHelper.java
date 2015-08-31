package com.jbrunton.pockettimeline.helpers;

import javax.inject.Inject;

public class RandomHelper {
    @Inject public RandomHelper() {

    }

    public int getNext(int max) {
        return (int) (Math.random() * max);
    }
}
