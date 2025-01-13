package org.denhez.pdf.tool.explorer;

import org.denhez.pdf.util.Pair;

import java.util.Deque;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public interface Explorer<U> {

    /**
     * Explores a text in a deque. The deque is made of parts of a full text (for example, each row of a text)
     *
     * @param text the text in a deque
     * @param delimiter delimiter to stop exploration when predicate is true
     * @return a pair of the remaining text (first) followed by the text found
     */
    Pair<Deque<String>,U> explore(Deque<String> text, Predicate<U> delimiter);

    /**
     * Variant of:
     * @see #explore(Deque, Predicate)
     * Explore elements by chunks and return a pair of the result using a separator
     *
     * @param text the text in a deque
     * @param delimiter delimiter to stop exploration when predicate is true
     * @param chunk chunk size
     * @param chunkSeparator chunk separator
     * @return a pair of the remaining text (first) followed by the text found (second) in chunk, separated by the
     * chunkSeparator
     */
    Pair<Deque<String>,U> explore(Deque<String> text, Predicate<U> delimiter, int chunk, String chunkSeparator);

    /**
     * Variant of:
     * @see #explore(Deque, Predicate)
     * Just applying a leap as delimiter to stop exploration 
     *
     * @param text the text in a deque
     * @param leap the leap to apply 
     * @return a pair of the remaining text (first) followed by the text found (second) after the leap is made.
     */
    Pair<Deque<String>,U> explore(Deque<String> text, int leap);

    /**
     * Variant of
     * @see #explore(Deque, int)
     * with unary operator to alter then second element of the pair
     *
     * @param text the text in a deque
     * @param leap the leap to apply
     * @param uo the unary operator
     * @return @return a pair of the remaining text (first) followed by the text found (second, transformed by the
     * unary operator) after the leap is made.
     */
    Pair<Deque<String>,U> explore(Deque<String> text, int leap, UnaryOperator<U> uo);

    /**
     * Variant of
     * @see #explore(Deque, Predicate, int, String)
     * but with a leap instead of Predicate
     *
     * @param text the text in a deque
     * @param leap the leap to apply
     * @param chunk chunk size
     * @param chunkSeparator chunk separator
     * @return a pair of the remaining text (first) followed by the text found (second) after the leap is made,
     * in chunk, separated by the chunkSeparator
     */
    Pair<Deque<String>,U> explore(Deque<String> text, int leap, int chunk, String chunkSeparator);

}
