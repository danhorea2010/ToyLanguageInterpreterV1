package model.statement;

import exceptions.VariableTypeMismatchException;
import model.ProgramState;
import model.adt.MyDictionary;
import model.expression.Expression;
import model.types.StringType;
import model.types.Type;
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

    @Override
    public MyDictionary<String, Type> typeCheck(MyDictionary<String, Type> typeEnvironment) throws Exception {
        Type type = expression.typeCheck(typeEnvironment);

        // Type must be string
        if(!type.equals(new StringType()))
            throw new VariableTypeMismatchException("CloseReadFile: Parameter must be a string");

        return typeEnvironment;
    }


}
