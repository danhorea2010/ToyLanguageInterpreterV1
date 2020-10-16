package model.expression;

import model.IDictionary;
import model.types.IntType;
import model.values.IntValue;
import model.values.Value;

public class ArithmeticExpression implements Expression{

    Expression expression1;
    Expression expression2;
    int operator;

    public ArithmeticExpression(Expression expression1, Expression expression2){
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    enum OperatorType{
        Addition,
        Subtraction,
        Multiplication,
        Division,
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl) throws Exception {
        Value value1,value2;

        value1 = expression1.eval(tbl);
        if(value1.getType().equals(new IntType())   )
        {
            value2 = expression2.eval(tbl);
            if(value2.getType().equals(new IntType())   )
            {
                IntValue int1 = (IntValue)value1;
                IntValue int2 = (IntValue)value2;

                int n1,n2;
                n1 = int1.getValue();
                n2 = int2.getValue();
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
                        if(n2 == 0) throw new Exception("Division by zero\n");
                        else return new IntValue(n1/n2);
                    }
                }
            }
            else
                throw new Exception("Second operand is not an integer\n");
        }else
            throw new Exception("First operand is not an integer\n");

        return null;
    }


}
