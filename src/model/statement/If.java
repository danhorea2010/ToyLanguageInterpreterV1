package model.statement;

import exceptions.VariableTypeMismatchException;
import model.ProgramState;
import model.adt.MyDictionary;
import model.adt.MyStack;
import model.expression.Expression;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.Value;


public class If implements IStatement {

    Expression expression;
    IStatement thenStatement;
    IStatement elseStatement;

    public If(Expression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public String toString() {
        return "if " + expression + " then " + thenStatement + " else " + elseStatement + "";
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        MyDictionary<String, Value> symbolTable = (MyDictionary<String, Value>) state.getSymbolTable();
        MyStack<IStatement> stack = (MyStack<IStatement>) state.getStack();

        Value condition = expression.eval(symbolTable, state.getHeapTable() );

        if(!condition.getType().equals(new BoolType()))
        {
            throw new VariableTypeMismatchException("Condition expression is not boolean");
        }
        else {
            if(((BoolValue) condition).getValue()){
                stack.push(thenStatement);
            }
            else{
                stack.push(elseStatement);
            }
        }

        return null;
    }


}
