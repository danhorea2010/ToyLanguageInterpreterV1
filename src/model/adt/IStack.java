package model.adt;

import exceptions.ReadFromEmptyException;

import java.util.stream.Stream;

public interface IStack<T> {
    T pop() throws ReadFromEmptyException;
    T peek() throws ReadFromEmptyException;
    void push(T value);
    void clear();
    boolean isEmpty();

    Stream<T> stream();


}
