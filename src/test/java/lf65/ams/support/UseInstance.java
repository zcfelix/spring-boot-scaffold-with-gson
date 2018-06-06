package lf65.ams.support;

public interface UseInstance<T, X extends Throwable> {
    String accept(T instance) throws X;
}
