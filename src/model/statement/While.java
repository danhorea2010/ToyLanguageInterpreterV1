package model.statement;

import exceptions.VariableTypeMismatchException;
import model.ProgramState;
import model.adt.MyDictionary;
import model.adt.MyStack;
import model.expression.Expression;
import model.types.BoolType;
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

        Value condition = expression.eval(symbolTable);

        if(!condition.getType().equals(new BoolType()))
        {
            throw new VariableTypeMismatchException("Condition expression is not boolean");
        }

        if(((BoolValue) condition).getValue()){
            stack.push(this);
            stack.push(thenStatement);
        }

        return state;
    }


}
