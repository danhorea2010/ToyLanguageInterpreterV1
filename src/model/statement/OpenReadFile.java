package model.statement;

import model.ProgramState;
import model.expression.Expression;
import model.types.StringType;
import model.values.StringValue;
import model.values.Value;

public class OpenReadFile implements IStatement {

    private final Expression expression;

    public OpenReadFile(Expression expr){
        this.expression = expr;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {

        Value value = expression.eval(state.getSymbolTable());
        if (!value.getType().equals(new StringType())){
            // error out
            throw new RuntimeException("File path must be string\n");
        }

        if( state.getFileTable().get((StringValue) value) != null){
            // error out
            throw new RuntimeException("File already in file table\n");
        }



        return state;
    }
}
