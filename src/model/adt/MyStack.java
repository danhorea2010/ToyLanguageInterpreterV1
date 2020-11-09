package model.adt;

import exceptions.ReadFromEmptyException;

import java.util.Stack;
import java.util.stream.Stream;

public class MyStack<T> implements IStack<T>{

    private final Stack<T> stack;

    public MyStack(){
        stack = new Stack<>();
    }

    @SuppressWarnings("unchecked")
    public MyStack(MyStack<T> oldStack) {
        stack = (Stack<T>) oldStack.stack.clone();
    }

    @Override
    public T pop() throws ReadFromEmptyException {
        T object = stack.pop();
        if(object == null){
            throw new ReadFromEmptyException("Stack is empty");
        }

        return object;
    }

    @Override
    public T peek() throws ReadFromEmptyException {
        T object = stack.peek();
        if( object == null){
            throw new ReadFromEmptyException("Stack is empty");
        }
        return object;
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

    @Override
    public Stream<T> stream() {
        return this.stack.stream();
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
