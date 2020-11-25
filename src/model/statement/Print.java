package model.statement;

import model.ProgramState;
import model.expression.Expression;

public class Print implements IStatement {

   Expression expression;

   public Print(Expression expression){
       this.expression = expression;

   }

   @Override
    public String toString() {
        return "Print (" +
                expression.toString() +
                ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
       var myList =  state.getOutput();
        myList.add(expression.eval(state.getSymbolTable(), state.getHeapTable()));

        return null;
    }


}
