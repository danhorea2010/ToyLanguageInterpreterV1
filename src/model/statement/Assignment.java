package model.statement;

import exceptions.VariableNotDeclaredException;
import exceptions.VariableTypeMismatchException;
import model.ProgramState;
import model.adt.MyDictionary;
import model.expression.Expression;
import model.types.Type;
import model.values.Value;

public class Assignment implements  IStatement{

    String id;
    Expression expression;

    public Assignment(String id , Expression expression){
        this.id = id;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return  id + " = " + expression ;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        MyDictionary<String, Value> symbolTable = (MyDictionary<String, Value>) state.getSymbolTable();

        if (symbolTable.get(id) != null) {
            Value value1 = expression.eval(symbolTable, state.getHeapTable());
            if (value1.getType().equals(symbolTable.get(id).getType())) {
                symbolTable.put(id, value1);
            } else
                throw new VariableTypeMismatchException("Type of expression and type of variable do not match\n");

        } else throw new VariableNotDeclaredException("Variable id not declared\n");


        return null;
    }

    @Override
    public MyDictionary<String, Type> typeCheck(MyDictionary<String, Type> typeEnvironment) throws Exception {
        Type typeVariable = typeEnvironment.get(id);
        Type typeExpression  = expression.typeCheck(typeEnvironment);

        // Should update the typeEnvironment?

        if(typeVariable.equals(typeExpression)){
            return typeEnvironment;
        }
        else
            throw new VariableTypeMismatchException("Assignment: right and left hand side have different types");

    }


}
