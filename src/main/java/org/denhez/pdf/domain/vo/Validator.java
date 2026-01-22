package org.denhez.pdf.domain.vo;

import java.util.function.Predicate;

public class Validator <T> {

    private final T t;
    private final IllegalStateException error;

    private Validator(T t, IllegalStateException error) {
        this.t = t;
        this.error = error;
    }

    public static <T> Validator<T> of(T t) {
        return new Validator<>(t, null);
    }

    public Validator<T> validate(Predicate<T> validation, String message) {
        if(error == null && !validation.test(t)) {
            return new Validator<>(t, new IllegalStateException(message));
        }
        return this;
    }

    public T get() {
        if(error == null) {
            return t;
        }
        throw error;
    }
}
