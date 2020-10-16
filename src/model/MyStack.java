package model;

import java.util.Stack;

public class MyStack<T> implements IStack<T>{

    private final Stack<T> stack;

    public MyStack(){
        stack = new Stack<T>();
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T value) {
        stack.push(value);
    }

    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public boolean isEmpty() {
        return stack.empty();
    }

}
