package com.epam.rd.autocode.spring.project.util;

import java.util.Optional;
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
        } else if(this.value != null) {
            return new Boxed<>(mapper.apply(this.value));
        }
        return Boxed.of(null);
    }

    public Boxed<T> filter(Predicate<T> criteria) {
        if (criteria == null) {
            throw new IllegalArgumentException("Predicate can't be null");
        } else if (criteria.test(this.value)) {
            return this;
        }
        return Boxed.of(null);
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

    public void ifPresentOrElseThrow(Consumer<T> consumer, Supplier<? extends RuntimeException> error) {
        if(this.value == null || consumer == null) {
           throw  error == null ? new IllegalArgumentException("No Error was supplied!") : error.get();
        } else {
            consumer.accept(this.value);
        }
    }

    public void ifPresent(Consumer<T> consumer) {
        if(consumer == null) {
            throw  new IllegalArgumentException("No Error was supplied!");
        } else if(value != null){
            consumer.accept(this.value);
        }
    }

    public <U> Boxed<U> flatOpt(Function<T, Optional<U>> mapper) {
        if (mapper == null) {
            throw new IllegalArgumentException("Function can't be null");
        }
        return Boxed.of(mapper.apply(this.value).orElse(null));
    }
}
