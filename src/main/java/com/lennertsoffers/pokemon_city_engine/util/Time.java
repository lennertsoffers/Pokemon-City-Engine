package com.lennertsoffers.pokemon_city_engine.util;

public class Time {
    public static float TIME_STARTED = System.nanoTime();

    public static float getTimeRunning() {
        return (float) ((System.nanoTime() - TIME_STARTED) * 1E-9);
    }
}
