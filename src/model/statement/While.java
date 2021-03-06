package model.statement;

import exceptions.VariableTypeMismatchException;
import model.ProgramState;
import model.adt.MyDictionary;
import model.adt.MyStack;
import model.expression.Expression;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class While implements IStatement {

    Expression expression;
    IStatement thenStatement;

    public While(Expression expression, IStatement thenStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        MyDictionary<String, Value> symbolTable = (MyDictionary<String, Value>) state.getSymbolTable();
        MyStack<IStatement> stack = (MyStack<IStatement>) state.getStack();

        Value condition = expression.eval(symbolTable, state.getHeapTable());

        if(!condition.getType().equals(new BoolType()))
        {
            throw new VariableTypeMismatchException("Condition expression is not boolean");
        }

        if(((BoolValue) condition).getValue()){
            stack.push(this);
            stack.push(thenStatement);
        }

        return null;
    }

    @Override
    public MyDictionary<String, Type> typeCheck(MyDictionary<String, Type> typeEnvironment) throws Exception {

        Type typeExp = expression.typeCheck(typeEnvironment);

        if( typeExp.equals(new BoolType())){
            thenStatement.typeCheck((MyDictionary<String, Type>) typeEnvironment.Clone());
            return typeEnvironment;
        }
        else
            throw new VariableTypeMismatchException("Parameter of While statement must be a boolean");

    }

    @Override
    public String toString() {
        return "While(" + expression + ") do " + thenStatement + " endwhile";
    }


}
