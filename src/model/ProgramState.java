package model;


import model.statement.IStatement;
import model.values.Value;

public class ProgramState {

    MyStack<IStatement> executionStack;
    MyDictionary<String, Value> symbolTable;
    MyList<Value> output;
    IStatement originalProgram;

    public ProgramState(MyStack<IStatement> executionStack, MyDictionary<String ,Value> symbolTable
            , MyList<Value> output ,IStatement program )
    {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;

        // Original program deepcopy

        executionStack.push(program);
    }

    public MyStack<IStatement> getStack() {
        return this.executionStack;
    }

}
