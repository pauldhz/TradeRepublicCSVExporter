package org.denhez.pdf.util;

public class Pair <T,U>{

    private T first;
    private U second;

    private Pair(T t, U u) {
        this.first = t;
        this.second = u;
    }

    public static <T,U> Pair<T,U> of(T t, U u) {
        return new Pair<>(t, u);
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }
}
