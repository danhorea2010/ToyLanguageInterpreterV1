package model.statement;

import model.MyStack;
import model.ProgramState;

public class CompareStatement implements IStatement {
    IStatement first;
    IStatement second;

    public CompareStatement(IStatement first, IStatement second){
        this.second = second;
        this.first = first;

    }

    @Override
    public String toString() {
        return "CompareStatement{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
    
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        MyStack<IStatement> stack = state.getStack();
        stack.push(second);
        stack.push(first);

        return state;
    }
}
