package model.statement;

import model.adt.MyStack;
import model.ProgramState;

public class CompStatement implements IStatement {
    IStatement first;
    IStatement second;

    public CompStatement(IStatement first, IStatement second){
        this.second = second;
        this.first = first;

    }

    @Override
    public String toString() {
        return "{" +
                " " + first +
                " , " + second +
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
