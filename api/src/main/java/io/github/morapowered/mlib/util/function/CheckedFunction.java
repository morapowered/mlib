package io.github.morapowered.mlib.util.function;

@FunctionalInterface
public interface CheckedFunction<T, R, E extends Throwable> {

    R apply(T t) throws E;

}
