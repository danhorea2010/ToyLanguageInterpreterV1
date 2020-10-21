package model.statement;

import model.adt.MyList;
import model.ProgramState;
import model.expression.Expression;
import model.values.Value;

public class Print implements IStatement {

   Expression expression;

   public Print(Expression expression){
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
        MyList<Value> myList = state.getOutput();
        myList.add(expression.eval(state.getSymbolTable()));

        return state;
    }


}
