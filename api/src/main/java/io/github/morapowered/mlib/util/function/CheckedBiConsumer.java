package io.github.morapowered.mlib.util.function;

@FunctionalInterface
public interface CheckedBiConsumer<T, L, E extends Throwable> {

    void accept(T t, L l) throws E;

}
