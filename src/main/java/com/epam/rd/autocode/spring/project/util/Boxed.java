package com.epam.rd.autocode.spring.project.util;

import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Boxed<T> {

    private T value;

    public Boxed(T value) {
        this.value = value;
    }

    public static <T> Boxed<T> of(T value) {
        return new Boxed<>(value);
    }

    public <E> Boxed<E> map(Function<T, E> mapper) {
        if (mapper == null) {
            throw new IllegalArgumentException("Function can't be null");
        }

        return new Boxed<>(mapper.apply(this.value));
    }

    public Boxed<T> filter(Predicate<T> criteria) {
        if (criteria == null) {
            throw new IllegalArgumentException("Predicate can't be null");
        } else if (criteria.test(this.value)) {
            return this;
        }
        return null;
    }

    public Boxed<T> doWith(Consumer<T> consumer) {
        if (consumer == null) {
            throw new IllegalArgumentException("Consumer can't be null");
        }
        consumer.accept(this.value);
        return this;
    }

    public T orElseThrow(Supplier<? extends RuntimeException> error) {
        if (error == null) {
            throw new NullPointerException();
        }else if(this.value == null) {
            throw error.get();
        }
        return this.value;
    }
}
