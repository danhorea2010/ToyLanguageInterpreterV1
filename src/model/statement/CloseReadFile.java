package model.statement;

import exceptions.VariableTypeMismatchException;
import model.ProgramState;
import model.expression.Expression;
import model.types.StringType;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;

public class CloseReadFile implements IStatement{

    private final Expression expression;

    public CloseReadFile(Expression expression){
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "close(" +
                expression +
                ')';
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {

        Value value = expression.eval(state.getSymbolTable(), state.getHeapTable() );
        if(!value.getType().equals(new StringType())){
            throw new VariableTypeMismatchException("Path must be string!\n");
        }

        StringValue stringValue = (StringValue) value;
        var fileTable = state.getFileTable();

        BufferedReader fileReader = fileTable.get(stringValue);
        if(fileReader == null){
            throw new RuntimeException("File " + stringValue.getValue() + " not open\n");
        }

        fileReader.close();
        fileTable.remove(stringValue);

        return null;
    }


}
