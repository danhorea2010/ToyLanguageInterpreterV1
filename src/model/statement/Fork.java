package model.statement;

import model.ProgramState;
import model.adt.MyStack;

public class Fork implements IStatement {

    IStatement statement;

    public Fork(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {

        var newStack = new MyStack<IStatement>();
        ProgramState newState = new ProgramState(newStack, state.getSymbolTable().Clone(), state.getOutput(), state.getFileTable(), state.getHeapTable(), statement);
        ProgramState.setID(ProgramState.getID() + 1);

        return newState;
    }

    @Override
    public String toString() {
        return "Fork(" + statement + ")";
    }
}
