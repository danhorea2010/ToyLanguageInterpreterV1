package model;


import model.adt.Heap;
import model.adt.IDictionary;
import model.adt.IList;
import model.adt.IStack;
import model.statement.Composite;
import model.statement.IStatement;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ProgramState {

    private final IStack<IStatement> executionStack;
    private final IDictionary<String, Value> symbolTable;
    private final IDictionary<StringValue, BufferedReader> fileTable;
    private final IList<Value> output;

    private final IStatement originalProgram;
    private final Heap heapTable;

    // This should be static?
    private static int ID;


    public ProgramState(IStack<IStatement> executionStack, IDictionary<String, Value> symbolTable
            , IList<Value> output, IDictionary<StringValue, BufferedReader> fileTable, Heap heapTable, IStatement program)
    {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heapTable = heapTable;

        this.originalProgram = deepCopy(program);

        executionStack.push(program);
    }

    public IStatement deepCopy(IStatement program){
        Composite newStatement = (Composite)program;
        return new Composite(newStatement.getFirst(), newStatement.getSecond());
    }

    public  static int getID() {
        return ProgramState.ID;
    }

    public  static synchronized  void setID(int ID) {
        ProgramState.ID = ID;
    }

    public IStack<IStatement> getStack() {
        return this.executionStack;
    }

    public IDictionary<String, Value> getSymbolTable() {return this.symbolTable;}
    public IDictionary<StringValue, BufferedReader> getFileTable() {return this.fileTable;}
    public IList<Value> getOutput() { return this.output; }
    public IStatement    getOriginalProgram() {return this.originalProgram;}
    public Heap getHeapTable() {return this.heapTable;}

    public void clearProgram(){
        this.executionStack.clear();
        this.symbolTable.clear();
        this.output.clear();
        this.heapTable.clear();
        // Need to close files in fileTable before clearing the table
        fileTable.values().forEach(v -> { try { v.close(); } catch (IOException e) { e.printStackTrace(); } });
        this.fileTable.clear();
    }

    // Print exec stack/symtable/output

    public String toString() {
        return "{" + "ID: " + ID + "\n" +
                "executionStack "+ ID+ " =" + executionStack +
                "}\nSymbolTable" + ID + " = { " + symbolTable + "}"
                +"\nOutput={" + output +"}"
                +"\nHeap={" + heapTable + "}";
    }

    public boolean isNotCompleted(){
        return !this.executionStack.isEmpty();
    }

    public ProgramState oneStep() throws Exception {
        if(executionStack.isEmpty())
        {
            throw new RuntimeException("Program stack is empty");
        }

        var currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }





}
