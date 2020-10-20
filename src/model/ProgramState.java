package model;


import model.adt.MyDictionary;
import model.adt.MyList;
import model.adt.MyStack;
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
    public MyDictionary<String, Value> getSymbolTable() {return this.symbolTable;}
    public MyList<Value> getOutput() { return this.output; }

    // Print exec stack/symtable/output

    public String toString() {
        return "{" +
                "executionStack=" + executionStack +
                "}\nSymbolTable= { " + symbolTable.toString() + "}"
                +"\nOutput={" + output.toString() +"}";
    }

}
