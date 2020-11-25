package model.statement;

import model.ProgramState;

public class Nop implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        return null;
    }

    @Override
    public String toString() {
        return "nop";
    }
}
