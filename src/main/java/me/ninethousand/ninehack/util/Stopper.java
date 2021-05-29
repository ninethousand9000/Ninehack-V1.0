package me.ninethousand.ninehack.util;

public final class Stopper {
    private long startTime = System.currentTimeMillis();

    public boolean hasTimePassed(final long ms) {
        return System.currentTimeMillis() - startTime > ms;
    }

    public void reset() {
        startTime = System.currentTimeMillis();
    }
}
