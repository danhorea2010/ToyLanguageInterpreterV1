package model.statement;

import model.ProgramState;
import model.adt.MyStack;

// FIXME: Completely broken
public class Fork implements IStatement {

    IStatement statement;

    public Fork(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        // This should return a new ProgramState

        var newStack = new MyStack<IStatement>();
        ProgramState newState = new ProgramState(newStack, state.getSymbolTable().Clone(), state.getOutput(), state.getFileTable(), state.getHeapTable(), statement);
//
//        IStack<IStatement> executionStack, IDictionary<String, Value > symbolTable
//                , IList<Value> output, IDictionary< StringValue, BufferedReader > fileTable, Heap heapTable, IStatement program

        // Should this be static??
        //ProgramState.setID(ProgramState.getID() + 1);
        ProgramState.setID(ProgramState.getID() + 1);
        //newState.getStack().push(statement);


        return newState;
    }

    @Override
    public String toString() {
        return "Fork(" + statement + ")";
    }
}
