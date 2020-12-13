package model.statement;

import model.ProgramState;
import model.adt.MyDictionary;
import model.expression.Expression;
import model.types.Type;

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

    @Override
    public MyDictionary<String, Type> typeCheck(MyDictionary<String, Type> typeEnvironment) throws Exception {

       // Return unchanged typeEnvironment
       expression.typeCheck(typeEnvironment);
       return typeEnvironment;
    }


}
