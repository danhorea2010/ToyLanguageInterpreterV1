package repository;

import model.ProgramState;
import model.adt.MyList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class StateRepository implements IRepository{

    private final String logfilePath;
    private MyList<ProgramState> stateList;

    public StateRepository(String _logFile){
        stateList = new MyList<>();
        this.logfilePath = _logFile;

    }


    @Override
    public void add(ProgramState state) {
        stateList.add(state);
    }

    @Override
    public void remove(ProgramState state) {
        stateList.remove(state);
    }


    @Override
    public void logProgramState(ProgramState state) throws RuntimeException, IOException {

        var logFile= new PrintWriter(new BufferedWriter(new FileWriter(logfilePath, true)));

        logFile.write("\nExecution Stack: \n");
        var stack = state.getStack();
        logFile.write("ID: " + state.getID() + "\n");


        logFile.write("" + stack + "\n");

        logFile.write("\nSymTable: \n");
        var symbolTable = state.getSymbolTable();
        logFile.write("" + symbolTable + "\n");

        logFile.write("\nOutput: \n");
        var output = state.getOutput();
        logFile.write("" + output + "\n");

        logFile.write("\nFileTable: \n");
        var fileTable = state.getFileTable();
        logFile.write("" + fileTable + "\n");

        logFile.write("\nHeap: \n");
        var heapTable = state.getHeapTable();
        logFile.write("" + heapTable + "\n");

        logFile.close();
    }

    @Override
    public MyList<ProgramState> getProgramList() {
        return new MyList<>(this.stateList);
    }

    @Override
    public void setProgramList(MyList<ProgramState> programList) {
        this.stateList = programList;
    }


}
