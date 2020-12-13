package model.statement;
import exceptions.VariableNotDeclaredException;
import exceptions.VariableTypeMismatchException;
import model.ProgramState;
import model.adt.MyDictionary;
import model.expression.Expression;
import model.types.IntType;
import model.types.StringType;
import model.types.Type;
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

        Value exprValue = expression.eval(symbolTable, state.getHeapTable());
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
            try {
                writeValue = Integer.parseInt(line);
            }catch (NumberFormatException e)
            {
                // throw exception?
                writeValue = 0;
            }
        }

        state.getSymbolTable().put(variableName, new IntValue(writeValue));

        return null;
    }

    @Override
    public MyDictionary<String, Type> typeCheck(MyDictionary<String, Type> typeEnvironment) throws Exception {
        Type type = expression.typeCheck(typeEnvironment);
        Type varType = typeEnvironment.get(variableName);

        // Variable must be an integer
        if(!varType.equals(new IntType()))
            throw new VariableTypeMismatchException("ReadFile: Variable must be an integer");

        // Type must be string
        if(!type.equals(new StringType()))
            throw new VariableTypeMismatchException("ReadFile: Parameter must be a string");

        return typeEnvironment;

    }


}
