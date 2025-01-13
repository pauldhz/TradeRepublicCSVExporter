package org.denhez.pdf.reader.tool.pruner;

import org.denhez.pdf.util.Pair;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public interface Pruner <T,U> {

    Pair<T,U> prune(T t, Predicate<U> delimiter);
    Pair<T,U> prune(T t, Predicate<U> delimiter, int chunk, String chunkSeparator);
    Pair<T,U> prune(T t, int leap);
    Pair<T,U> prune(T t, int leap, UnaryOperator<U> uo);
    Pair<T,U> prune(T t, int leap, int chunk, String chunkSeparator);

}
