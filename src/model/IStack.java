package model;

public interface IStack<T> {
    T pop();
    void push(T value);
    void clear();
    boolean isEmpty();


}
