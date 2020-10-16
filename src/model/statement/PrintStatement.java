package model.statement;

import model.ProgramState;
import model.expression.Expression;

public class PrintStatement implements IStatement {

   Expression expression;

   public PrintStatement(Expression expression){
       this.expression = expression;

   }

   @Override
    public String toString() {
        return "Print {" +
                expression.toString() +
                '}';
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        return state;
    }


}
