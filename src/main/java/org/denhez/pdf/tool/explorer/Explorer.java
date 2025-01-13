package org.denhez.pdf.tool.explorer;

import org.denhez.pdf.util.Pair;

import java.util.Deque;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public interface Explorer<U> {

    Pair<Deque<String>,U> explore(Deque<String> t, Predicate<U> delimiter);
    Pair<Deque<String>,U> explore(Deque<String> t, Predicate<U> delimiter, int chunk, String chunkSeparator);
    Pair<Deque<String>,U> explore(Deque<String> t, int leap);
    Pair<Deque<String>,U> explore(Deque<String> t, int leap, UnaryOperator<U> uo);
    Pair<Deque<String>,U> explore(Deque<String> t, int leap, int chunk, String chunkSeparator);

}
