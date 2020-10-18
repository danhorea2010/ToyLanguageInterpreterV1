package model.statement;

import model.adt.MyDictionary;
import model.ProgramState;
import model.expression.Expression;
import model.values.Value;

public class AssignmentStatement implements  IStatement{

    String id;
    Expression expression;

    public AssignmentStatement(String id , Expression expression){
        this.id = id;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "AssignmentStatement{" +
                "id='" + id + '\'' +
                ", expression=" + expression.toString() +
                '}';
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        MyDictionary<String, Value> symbolTable = state.getSymbolTable();

        if(symbolTable.get(id) != null){
            Value value1 = expression.eval(symbolTable);
            if(value1.getType().equals(symbolTable.get(id).getType()))
            {
                symbolTable.put(id, value1);
            }
            else throw new Exception("Type of expression and type of variable do not match");

        }   else throw new Exception("Variable id not declared");


        return state;
    }


}
