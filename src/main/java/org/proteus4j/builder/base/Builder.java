package org.proteus4j.builder.base;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Builder<T> {

    private final Supplier<T> instantiator;
    private final List<Consumer<T>> modifiers;

    private Builder(Supplier<T> instantiator) {
        this.instantiator = instantiator;
        this.modifiers = new ArrayList<>();
    }

    public static <T> Builder<T> of(Supplier<T> instance) {
        return new Builder<>(instance);
    }

    public <V> Builder<T> with(BiConsumer<T, V> consumer, V value) {
        Consumer<T> c = instance -> consumer.accept(instance, value);
        modifiers.add(c);
        return this;
    }

    public T build() {
        T instance = instantiator.get();
        return modify(instance);
    }

    private T modify(T instance) {
        modifiers.forEach(modifier -> modifier.accept(instance));
        modifiers.clear();
        return instance;
    }
}
