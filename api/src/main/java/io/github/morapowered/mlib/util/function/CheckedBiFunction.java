package io.github.morapowered.mlib.util.function;

@FunctionalInterface
public interface CheckedBiFunction<T, L, R, E extends Throwable> {

    R apply(T t, L l) throws E;

}
