package model.statement;

import model.ProgramState;
import model.expression.Expression;
import model.types.StringType;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.FileReader;

public class OpenReadFile implements IStatement {

    private final Expression expression;

    public OpenReadFile(Expression expr){
        this.expression = expr;
    }

    @Override
    public String toString() {
        return "open(" +
                expression +
                ')';
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {

        Value value = expression.eval(state.getSymbolTable(), state.getHeapTable());
        var fileTable = state.getFileTable();

        if (!value.getType().equals(new StringType())){
            // error out
            throw new RuntimeException("File path must be a string\n");
        }

        StringValue stringValue = (StringValue) value;

        if( fileTable.get(stringValue) != null){
            // error out
            throw new RuntimeException("File already in file table\n");
        }

        String toOpen = stringValue.getValue();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(toOpen));

        fileTable.put(stringValue, bufferedReader);

        return state;
    }
}
