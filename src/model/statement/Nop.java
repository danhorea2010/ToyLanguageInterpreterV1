package model.statement;

import model.ProgramState;
import model.adt.MyDictionary;
import model.types.Type;

public class Nop implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        return null;
    }

    @Override
    public MyDictionary<String, Type> typeCheck(MyDictionary<String, Type> typeEnvironment) throws Exception {
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "nop";
    }
}
