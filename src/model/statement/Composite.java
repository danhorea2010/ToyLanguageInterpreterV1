package model.statement;

import model.adt.MyStack;
import model.ProgramState;

public class Composite implements IStatement {
    IStatement first;
    IStatement second;


    public Composite(IStatement first, IStatement second){
        this.second = second;
        this.first = first;

    }

    @Override
    public String toString() {
        return "" +
                " " +  first +
                ", " + second +
                "";
    }

    public IStatement getFirst() {
        return first;
    }
    public IStatement getSecond() {
        return second;
    }


    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        MyStack<IStatement> stack = (MyStack<IStatement>) state.getStack();
        stack.push(second);
        stack.push(first);

        return state;
    }
}
