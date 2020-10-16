package model.statement;

import model.ProgramState;
import model.types.Type;

public class VariableDeclarationStatement implements IStatement {
    String name;
    Type type;


    public VariableDeclarationStatement(String name,Type type) {
        this.name = name;
        this.type = type;
    }


    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        return null;
    }

}
