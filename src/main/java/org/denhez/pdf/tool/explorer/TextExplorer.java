package org.denhez.pdf.tool.explorer;

import org.denhez.pdf.util.Pair;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class TextExplorer implements Explorer<String> {

    @Override
    public Pair<Deque<String>, String> explore(Deque<String> textQueue, Predicate<String> delimiter) {
        return explore(textQueue, delimiter, 1, "");
    }

    @Override
    public Pair<Deque<String>, String> explore(Deque<String> textQueue, Predicate<String> delimiter, int chunk, String chunkSeparator) {
        while(!textQueue.isEmpty()) {
            StringBuilder current = new StringBuilder(textQueue.poll());
            Deque<String> copy = new ArrayDeque<>(textQueue);
            for(int i=1; i<chunk; i++) {
                current.append(chunkSeparator).append(copy.poll());
            }
            if(delimiter.test(current.toString())) {
                return Pair.of(textQueue, current.toString());
            }
        }
        return Pair.of(new ArrayDeque<>(), "end");
    }

    @Override
    public Pair<Deque<String>, String> explore(Deque<String> textQueue, int leap) {
        if (textQueue.isEmpty()) {
            return Pair.of(new ArrayDeque<>(), "end");
        }
        var current = textQueue.peek();
        for(int i=0; i<leap; i++) {
            current = textQueue.poll();
            if(textQueue.isEmpty()) {
                return Pair.of(new ArrayDeque<>(), current);
            }
        }
        return Pair.of(textQueue, current);
    }

    @Override
    public Pair<Deque<String>, String> explore(Deque<String> textQueue, int leap, UnaryOperator<String> uo) {
        var explore = explore(textQueue, leap);
        return Pair.of(explore.getFirst(), uo.apply(explore.getSecond()));
    }

    @Override
    public Pair<Deque<String>, String> explore(Deque<String> textQueue, int leap, int chunk, String chunkSeparator) {
        if (textQueue.isEmpty()) {
            return Pair.of(new ArrayDeque<>(), "end");
        }
        StringBuilder current = new StringBuilder().append(textQueue.peek());
        for(int i=0; i<leap; i++) {
            textQueue.poll();
            if(textQueue.isEmpty()) {
                return Pair.of(new ArrayDeque<>(), current.toString());
            }
            Deque<String> copy = new ArrayDeque<>(textQueue);
            for(int j=0; j<chunk; j++) {
                current.append(copy.poll());
            }
        }
        return Pair.of(textQueue, current.toString());
    }
}
