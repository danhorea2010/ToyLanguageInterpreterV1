package model.statement;

import model.ProgramState;
import model.expression.Expression;

public class New implements IStatement {

    private String variableName;
    private Expression expression;

    public New(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {

        


        return state;
    }


}
