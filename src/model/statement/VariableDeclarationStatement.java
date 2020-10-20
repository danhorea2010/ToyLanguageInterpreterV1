package model.statement;

import model.adt.MyDictionary;
import model.ProgramState;
import model.types.BoolType;
import model.types.IntType;
import model.types.Type;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

public class VariableDeclarationStatement implements IStatement {
    private static final int     DEFAULT_INT_VALUE  = 0;
    private static final boolean DEFAULT_BOOL_VALUE = false;

    String name;
    Type type;


    public VariableDeclarationStatement(String name,Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return  type + " " + name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        MyDictionary<String, Value> symbolTable = state.getSymbolTable();

            if (symbolTable.get(name) == null) {
                if (type.equals(new IntType())) {
                    symbolTable.put(name, new IntValue(DEFAULT_INT_VALUE));
                } else if (type.equals(new BoolType())) {
                    symbolTable.put(name, new BoolValue(DEFAULT_BOOL_VALUE));
                }
            }

        return state;
    }

}
