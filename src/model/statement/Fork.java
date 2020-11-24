package model.statement;

import model.ProgramState;

public class Fork implements IStatement {

    IStatement statement;

    public Fork(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {

        return state;
    }


}
