package model;


import model.adt.MyDictionary;
import model.adt.MyList;
import model.adt.MyStack;
import model.statement.Composite;
import model.statement.IStatement;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.File;

public class ProgramState {

    private final MyStack<IStatement> executionStack;
    private final MyDictionary<String, Value> symbolTable;
    private final MyDictionary<StringValue, BufferedReader> fileTable;
    private final MyList<Value> output;

    private final IStatement originalProgram;

    public ProgramState(MyStack<IStatement> executionStack, MyDictionary<String ,Value> symbolTable
            , MyList<Value> output , MyDictionary<StringValue, BufferedReader> fileTable,IStatement program )
    {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.originalProgram = deepCopy(program);

        executionStack.push(program);
    }

    public IStatement deepCopy(IStatement program){
        Composite newStatement = (Composite)program;
        return new Composite(newStatement.getFirst(), newStatement.getSecond());
    }

    public MyStack<IStatement> getStack() {
        return this.executionStack;
    }
    public MyDictionary<String, Value> getSymbolTable() {return this.symbolTable;}
    public MyDictionary<StringValue, BufferedReader> getFileTable() {return this.fileTable;}
    public MyList<Value> getOutput() { return this.output; }
    public IStatement    getOriginalProgram() {return this.originalProgram;}

    public void clearProgram(){
        this.executionStack.clear();
        this.symbolTable.clear();
        this.output.clear();
        this.fileTable.clear();
    }

    // Print exec stack/symtable/output

    public String toString() {
        return "{" +
                "executionStack=" + executionStack +
                "}\nSymbolTable= { " + symbolTable + "}"
                +"\nOutput={" + output +"}";
    }

}
