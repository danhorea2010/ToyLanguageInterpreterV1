package model.statement;

import model.MyStack;
import model.ProgramState;

public class CompareStatement implements IStatement {
    IStatement first;
    IStatement second;

    @Override
    public String toString() {
        return "CompareStatement{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
    
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        // TODO: Implement MyIStack and MyIDictionary and ILIst
        MyStack<IStatement> stack = state.getStack();
        stack.push(second);
        stack.push(first);

        return state;
    }
}
