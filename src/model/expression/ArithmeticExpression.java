package model.expression;

import exceptions.DivisionByZeroException;
import exceptions.VariableTypeMismatchException;
import model.adt.Heap;
import model.adt.IDictionary;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class ArithmeticExpression implements Expression{

    private static final int INVALID_OPERATOR = 4;

    private final Expression expression1;
    private final Expression expression2;
    private final int operator;

    public ArithmeticExpression(char operator,Expression expression1, Expression expression2){

        switch (operator){
            case '+' -> this.operator = 0;
            case '-' -> this.operator = 1;
            case '*' -> this.operator = 2;
            case '/' -> this.operator = 3;
            default -> this.operator = INVALID_OPERATOR;
        }

        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    private enum OperatorType{
        Addition,
        Subtraction,
        Multiplication,
        Division,
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl, Heap heapTable) throws Exception {
        Value value1,value2;

        // Invalid operator
        if( this.operator == INVALID_OPERATOR){
            throw new VariableTypeMismatchException("Invalid arithmetic operator");
        }

        value1 = expression1.eval(tbl, heapTable);
        if(value1.getType().equals(new IntType())   )
        {
            value2 = expression2.eval(tbl, heapTable);
            if(value2.getType().equals(new IntType())   )
            {
                int n1,n2;

                n1 = ((IntValue) value1).getValue();
                n2 = ((IntValue) value2).getValue();

                OperatorType operatorType = OperatorType.values()[operator];

                switch (operatorType){
                    case Addition -> {
                        return new IntValue(n1+n2);
                    }
                    case Subtraction -> {
                        return new IntValue(n1-n2);
                    }
                    case Multiplication -> {
                        return new IntValue(n1*n2);
                    }
                    case Division -> {
                        if(n2 == 0) throw new DivisionByZeroException("Division by zero\n");
                        else return new IntValue(n1/n2);
                    }
                }
            }
            else
                throw new VariableTypeMismatchException("Second operand is not an integer\n");
        }else
            throw new VariableTypeMismatchException("First operand is not an integer\n");

        return null;
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws Exception {
        Type type1, type2;

        type1 = expression1.typeCheck(typeEnvironment);
        type2 = expression2.typeCheck(typeEnvironment);

        if (!type1.equals(new IntType()))
            throw new VariableTypeMismatchException("First operand must be an integer");
        if (!type2.equals(new IntType()))
            throw new VariableTypeMismatchException("Second operand must be an int");

        return new IntType();
    }

    @Override
    public String toString() {
        // Maybe should be refactored
        char displayOp;

        switch (operator){
            case 0 -> displayOp = '+';
            case 1 -> displayOp = '-';
            case 2 -> displayOp = '*';
            case 3 -> displayOp = '/';
            default -> displayOp = ' ';
        }

        return  "" + expression1 + displayOp + expression2;
    }

}
