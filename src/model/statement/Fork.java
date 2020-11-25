package model.statement;

import model.ProgramState;

public class Fork implements IStatement {

    IStatement statement;

    public Fork(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        // This should return a new ProgramState
        ProgramState newState = new ProgramState(state.getStack(), state.getSymbolTable().Clone(), state.getOutput(), state.getFileTable(), state.getHeapTable(),state.getOriginalProgram());
//
//        IStack<IStatement> executionStack, IDictionary<String, Value > symbolTable
//                , IList<Value> output, IDictionary< StringValue, BufferedReader > fileTable, Heap heapTable, IStatement program

        // Should this be static??
        ProgramState.setID(ProgramState.getID() + 1);


        return newState;
    }


}
