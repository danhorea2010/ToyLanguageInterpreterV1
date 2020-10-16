package model.statement;

import model.ProgramState;
import model.expression.Expression;

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
        return null;
    }


}
