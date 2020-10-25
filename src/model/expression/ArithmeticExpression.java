package model.expression;

import exceptions.DivisionByZeroException;
import exceptions.VariableTypeMismatchException;
import model.adt.IDictionary;
import model.types.IntType;
import model.values.IntValue;
import model.values.Value;

public class ArithmeticExpression implements Expression{

    private final Expression expression1;
    private final Expression expression2;
    private final int operator;

    public ArithmeticExpression(char operator,Expression expression1, Expression expression2){

        switch (operator){
            case '+' -> this.operator = 0;
            case '-' -> this.operator = 1;
            case '*' -> this.operator = 2;
            case '/' -> this.operator = 3;
            default -> this.operator = 4;
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
    public Value eval(IDictionary<String, Value> tbl) throws Exception {
        Value value1,value2;

        // Invalid operator
        if( this.operator == 4){
            throw new VariableTypeMismatchException("Invalid arithmetic operator");
        }

        value1 = expression1.eval(tbl);
        if(value1.getType().equals(new IntType())   )
        {
            value2 = expression2.eval(tbl);
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
