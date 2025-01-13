package org.denhez.pdf.tool.explorer;

import org.denhez.pdf.util.Pair;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public interface Explorer<T,U> {

    Pair<T,U> explore(T t, Predicate<U> delimiter);
    Pair<T,U> explore(T t, Predicate<U> delimiter, int chunk, String chunkSeparator);
    Pair<T,U> explore(T t, int leap);
    Pair<T,U> explore(T t, int leap, UnaryOperator<U> uo);
    Pair<T,U> explore(T t, int leap, int chunk, String chunkSeparator);

}
