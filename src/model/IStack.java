package model;

public interface IStack<T> {
    T pop();
    T peek();
    void push(T value);
    void clear();
    boolean isEmpty();



}
