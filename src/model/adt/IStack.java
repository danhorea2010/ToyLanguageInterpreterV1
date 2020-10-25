package model.adt;

import java.util.stream.Stream;

public interface IStack<T> {
    T pop();
    T peek();
    void push(T value);
    void clear();
    boolean isEmpty();

    Stream<T> stream();


}
