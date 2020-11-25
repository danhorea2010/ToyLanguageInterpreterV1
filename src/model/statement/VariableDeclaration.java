package model.statement;

import model.ProgramState;
import model.adt.MyDictionary;
import model.types.Type;
import model.values.Value;

public class VariableDeclaration implements IStatement {

    private final String name;
    private final Type type;


    public VariableDeclaration(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return  type + " " + name ;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        MyDictionary<String, Value> symbolTable = (MyDictionary<String, Value>) state.getSymbolTable();

        if (symbolTable.get(name) == null) {

            symbolTable.put(name, type.defaultValue());

//            if (type.equals(new IntType())) {
//                symbolTable.put(name, new IntValue(DEFAULT_INT_VALUE));
//            } else if (type.equals(new BoolType())) {
//                symbolTable.put(name, new BoolValue(DEFAULT_BOOL_VALUE));
//            }
        }


        return null;
    }

}
