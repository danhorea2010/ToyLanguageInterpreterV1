package model.statement;

import model.MyDictionary;
import model.MyStack;
import model.ProgramState;
import model.expression.Expression;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.Value;


public class IfStatement implements IStatement {

    Expression expression;
    IStatement thenStatement;
    IStatement elseStatement;

    public IfStatement(Expression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public String toString() {
        return "IfStatement{" +
                "\nexpression=" + expression +
                "\nTHEN =" + thenStatement +
                "\nELSE =" + elseStatement +
                '}';
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        MyDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyStack<IStatement> stack = state.getStack();

        Value condition = expression.eval(symbolTable);

        if(!condition.getType().equals(new BoolType()))
        {
            throw new Exception("Condition expression is not boolean");
        }
        else {
            if(((BoolValue) condition).getValue()){
                stack.push(thenStatement);
            }
            else{
                stack.push(elseStatement);
            }
        }

        return state;
    }


}
