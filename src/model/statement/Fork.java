package model.statement;

import model.ProgramState;
import model.adt.MyDictionary;
import model.adt.MyStack;
import model.types.Type;

public class Fork implements IStatement {

    IStatement statement;

    public Fork(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {

        var newStack = new MyStack<IStatement>();
        ProgramState newState = new ProgramState(newStack, state.getSymbolTable().Clone(), state.getOutput(), state.getFileTable(), state.getHeapTable(), statement);
        newState.setID(ProgramState.ID + 1);

        return newState;
    }

    @Override
    public MyDictionary<String, Type> typeCheck(MyDictionary<String, Type> typeEnvironment) throws Exception {
        statement.typeCheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "Fork(" + statement + ")";
    }
}
