package model.statement;
import exceptions.VariableNotDeclaredException;
import exceptions.VariableTypeMismatchException;
import model.ProgramState;
import model.expression.Expression;
import model.types.IntType;
import model.types.StringType;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;

public class ReadFile implements IStatement {

    private final Expression expression;
    private final String variableName;

    public ReadFile(Expression expression, String variableName){
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public String toString() {
        return "readFile(" +
                expression +
                ", " + variableName +
                ')';
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        var symbolTable = state.getSymbolTable();

        Value value = symbolTable.get(variableName);
        if(value == null){
            throw new VariableNotDeclaredException("Variable " + variableName + " not declared\n");
        }

        if( !value.getType().equals(new IntType())){
            throw new VariableTypeMismatchException("Variable " + variableName + " must be int\n" );
        }

        Value exprValue = expression.eval(symbolTable);
        if(!exprValue.getType().equals(new StringType())){
            throw new RuntimeException("Path must be string!\n");
        }

        StringValue stringValue = (StringValue) exprValue;
        var fileTable = state.getFileTable();
        BufferedReader fileReader = fileTable.get(stringValue);

        if(fileReader == null){
            throw new RuntimeException("Cannot open file " + stringValue.getValue());
        }

        int writeValue = 0;
        String line = fileReader.readLine();
        if(line != null){
            writeValue = Integer.parseInt(line);
        }

        state.getSymbolTable().put(variableName, new IntValue(writeValue));

        return state;
    }


}
